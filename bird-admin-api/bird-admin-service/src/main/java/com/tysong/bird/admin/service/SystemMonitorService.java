package com.tysong.bird.admin.service;

import com.google.common.collect.Maps;
import com.tysong.bird.admin.common.bean.ChartBean;
import com.tysong.bird.admin.common.bean.JvmBean;
import com.tysong.bird.admin.common.config.ThreadPoolManager;
import com.tysong.bird.admin.common.config.TimeFormatConfig;
import com.tysong.bird.admin.common.utils.ColorUtil;
import com.tysong.bird.admin.common.utils.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.management.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class SystemMonitorService {

    public static final Integer MONITORHOURS = 24;//保留多长时间的监控数据
    private ChartBean jvmMemoryChart = new ChartBean();
    private ChartBean jvmThreadChart = new ChartBean();
    private ChartBean jvmGCChart = new ChartBean();
    private ChartBean diskChart = new ChartBean();
    private  List<GarbageCollectorMXBean> garbageCollectorMXBeans;
    private RuntimeMXBean runtimeMXBean;
    private MemoryMXBean memoryMXBean;
    private OperatingSystemMXBean operatingSystemMXBean;
    private AtomicBoolean isInit = new AtomicBoolean(false);
    private Map<String,ChartBean.DataSet> memoryDataSetMap = Maps.newHashMap();
    private Map<String,ChartBean.DataSet> threadStatueDataSetMap = Maps.newHashMap();
    private Map<String,ChartBean.DataSet> gcDataSetMap = Maps.newHashMap();
    private Map<String,ChartBean.DataSet> diskDataSetMap = Maps.newHashMap();
    private File disk=new File(".");
    private LocalDateTime startTime;

    public JvmBean getJvmInfo() {
        JvmBean jvmBean = new JvmBean();
        Long uptime = runtimeMXBean.getUptime();
        if (uptime<TimeUnit.MINUTES.toMillis(1)) {
            jvmBean.setUpTime(String.format("%.2f",uptime.doubleValue() / 1000.0) + "秒");
        }else if (uptime<TimeUnit.MINUTES.toMillis(60)){
            jvmBean.setUpTime(String.format("%.2f",uptime.doubleValue() / 60000.0) + "分");
        }else if (uptime<TimeUnit.MINUTES.toMillis(60*60)){
            jvmBean.setUpTime(String.format("%.2f",uptime.doubleValue() / 3600000.0) + "时");
        }else if (uptime<TimeUnit.MINUTES.toMillis(60*60*24)){
            jvmBean.setUpTime(String.format("%.2f",uptime.doubleValue() / (3600000.0*24.0)) + "天");
        }
        long startTime = runtimeMXBean.getStartTime();
        Instant instant = Instant.ofEpochMilli(startTime);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        jvmBean.setStartTime(TimeFormatConfig.formatter.format(localDateTime));
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        BigDecimal heapUsedDecimal = new BigDecimal(heapMemoryUsage.getUsed() * 100.0 / heapMemoryUsage.getMax());
        String heapUsed = heapUsedDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        jvmBean.setHeapUsed(heapUsed + "%");
        BigDecimal systemLoadAverage = new BigDecimal(operatingSystemMXBean.getSystemLoadAverage());
        jvmBean.setSystemLoadAverage(systemLoadAverage.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        jvmBean.setJvmMemoryChart(jvmMemoryChart);
        jvmBean.setJvmThreadChart(jvmThreadChart);
        jvmBean.setJvmGCChart(jvmGCChart);
        jvmBean.setDiskChart(diskChart);
        return jvmBean;
    }


    @PostConstruct
    public void init() {
        if (isInit.compareAndSet(false, true)) {
            startTime=LocalDateTime.now();
            initMXBean();
            initMemoryChart();
            initThreadChart();
            initGCChart();
            initDiskChart();
        }
        ThreadPoolManager.scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                log.info("[SystemMonitorService]定时任务运行");
                Duration between = Duration.between(startTime, LocalDateTime.now());
                if (between.toHours()>=MONITORHOURS){
                    clear(memoryDataSetMap.entrySet().iterator());
                    clear(threadStatueDataSetMap.entrySet().iterator());
                    clear(gcDataSetMap.entrySet().iterator());
                    clear(diskDataSetMap.entrySet().iterator());
                }
                buildJvmMemoryChart();
                buildThreadChart();
                buildGCChart();
                buildDiskChart();
            }catch (Exception ex){
                log.error("[SystemMonitorService]定时任务运行失败:",ex);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void clear(Iterator<Map.Entry<String, ChartBean.DataSet>> entryIterator) {
        while (entryIterator.hasNext()){
            Map.Entry<String, ChartBean.DataSet> entry = entryIterator.next();
            ChartBean.DataSet value = entry.getValue();
            value.getData().clear();
        }
    }

    private void buildDiskChart() {
        List<String> labels = diskChart.getLabels();
        LocalDateTime now = LocalDateTime.now();
        String label = TimeFormatConfig.formatter2.format(now);
        labels.add(label);

        diskChart.getDatasets().clear();
        long totalSpace = disk.getTotalSpace();
        long usableSpace = disk.getUsableSpace();
        ChartBean.DataSet dataSet = diskDataSetMap.get("disk");
        double usage=(totalSpace-usableSpace)*100.0/totalSpace;
        dataSet.getData().add(usage);
        diskChart.getDatasets().add(dataSet);
    }

    private void initDiskChart() {
        ChartBean.DataSet diskDataSet = new ChartBean.DataSet();
        diskDataSet.setLabel("disk_usage");
        diskDataSet.setFill(false);
        String randomColor = ColorUtil.getRandomColor();
        diskDataSet.setBackgroundColor(randomColor);
        diskDataSet.setBorderColor(randomColor);
        diskDataSetMap.put("disk",diskDataSet);
    }

    private void buildGCChart() {
        List<String> labels = jvmGCChart.getLabels();
        LocalDateTime now = LocalDateTime.now();
        String label = TimeFormatConfig.formatter2.format(now);
        labels.add(label);
        Iterator<Map.Entry<String, ChartBean.DataSet>> entryIterator = gcDataSetMap.entrySet().iterator();
        jvmGCChart.getDatasets().clear();
        while (entryIterator.hasNext()){
            Map.Entry<String, ChartBean.DataSet> entry = entryIterator.next();
            String key = entry.getKey();
            ChartBean.DataSet dataSet = entry.getValue();
            if (CollectionUtils.isNotEmpty(garbageCollectorMXBeans)){
                for (int i=0;i<garbageCollectorMXBeans.size();i++){
                    GarbageCollectorMXBean garbageCollectorMXBean = garbageCollectorMXBeans.get(i);
                    if (StringUtils.equals(garbageCollectorMXBean.getName(),key)){
                        long gcTime = garbageCollectorMXBean.getCollectionTime();
                        dataSet.getData().add((double) gcTime);
                        jvmGCChart.getDatasets().add(dataSet);
                    }
                }
            }
        }
    }

    private void initGCChart() {
        if (CollectionUtils.isNotEmpty(garbageCollectorMXBeans)){
            for (int i=0;i<garbageCollectorMXBeans.size();i++){
                GarbageCollectorMXBean garbageCollectorMXBean = garbageCollectorMXBeans.get(i);
                String name = garbageCollectorMXBean.getName();
                ChartBean.DataSet gcDataSet = new ChartBean.DataSet();
                gcDataSet.setLabel(name);
                gcDataSet.setFill(false);
                String randomColor = ColorUtil.getRandomColor();
                gcDataSet.setBackgroundColor(randomColor);
                gcDataSet.setBorderColor(randomColor);
                gcDataSetMap.put(name,gcDataSet);
            }
        }
    }

    private void buildThreadChart() {
        List<String> labels = jvmThreadChart.getLabels();
        LocalDateTime now = LocalDateTime.now();
        String label = TimeFormatConfig.formatter2.format(now);
        labels.add(label);

        Map<String, Thread> threads = ThreadUtil.getThreads();
        Map<Thread.State, Integer> stateCountMap = Maps.newHashMap();
        for (Thread.State s : Thread.State.values()) {
            stateCountMap.put(s, 0);
        }
        for (Thread thread : threads.values()) {
            Thread.State threadState = thread.getState();
            Integer count = stateCountMap.get(threadState);
            stateCountMap.put(threadState, count + 1);
        }
        jvmThreadChart.getDatasets().clear();
        Iterator<Map.Entry<Thread.State, Integer>> entryIterator =
                stateCountMap.entrySet().iterator();
        while (entryIterator.hasNext()){
            Map.Entry<Thread.State, Integer> entry = entryIterator.next();
            Thread.State key = entry.getKey();
            Integer value = entry.getValue();
            ChartBean.DataSet dataSet = threadStatueDataSetMap.get(key.name());
            dataSet.getData().add(value.doubleValue());
            jvmThreadChart.getDatasets().add(dataSet);
        }
    }

    private void initThreadChart() {
        Map<String, Thread> threads = ThreadUtil.getThreads();
        Map<Thread.State, Integer> stateCountMap = Maps.newHashMap();
        for (Thread.State s : Thread.State.values()) {
            stateCountMap.put(s, 0);
        }
        for (Thread thread : threads.values()) {
            Thread.State threadState = thread.getState();
            Integer count = stateCountMap.get(threadState);
            stateCountMap.put(threadState, count + 1);
        }
        Iterator<Map.Entry<Thread.State, Integer>> entryIterator =
                stateCountMap.entrySet().iterator();
        while (entryIterator.hasNext()){
            Map.Entry<Thread.State, Integer> entry = entryIterator.next();
            Thread.State key = entry.getKey();
            ChartBean.DataSet threadStatueDataSet = new ChartBean.DataSet();
            threadStatueDataSet.setLabel(key.name());
            threadStatueDataSet.setFill(false);
            String randomColor = ColorUtil.getRandomColor();
            threadStatueDataSet.setBorderColor(randomColor);
            threadStatueDataSet.setBackgroundColor(randomColor);
            threadStatueDataSetMap.put(key.name(),threadStatueDataSet);
        }
    }

    private void initMXBean() {
        runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        memoryMXBean = ManagementFactory.getMemoryMXBean();
        operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
    }

    private void initMemoryChart() {
        ChartBean.DataSet memoryUsedDataSet = new ChartBean.DataSet();
        memoryUsedDataSet.setLabel("jvm_memory_used");
        memoryUsedDataSet.setFill(false);
        memoryUsedDataSet.setBackgroundColor("#2f4860");
        memoryUsedDataSet.setBorderColor("#2f4860");
        ChartBean.DataSet memoryCommitDataSet = new ChartBean.DataSet();
        memoryCommitDataSet.setLabel("jvm_memory_commit");
        memoryCommitDataSet.setFill(false);
        memoryCommitDataSet.setBackgroundColor("#00bb7e");
        memoryCommitDataSet.setBorderColor("#00bb7e");
        ChartBean.DataSet memoryMaxDataSet = new ChartBean.DataSet();
        memoryMaxDataSet.setLabel("jvm_memory_max");
        memoryMaxDataSet.setFill(false);
        memoryMaxDataSet.setBackgroundColor("#FFD700");
        memoryMaxDataSet.setBorderColor("#FFD700");
        memoryDataSetMap.put("memoryUsedDataSet",memoryUsedDataSet);
        memoryDataSetMap.put("memoryCommitDataSet",memoryCommitDataSet);
        memoryDataSetMap.put("memoryMaxDataSet",memoryMaxDataSet);
    }

    public void buildJvmMemoryChart(){
        List<String> labels = jvmMemoryChart.getLabels();
        LocalDateTime now = LocalDateTime.now();
        String label = TimeFormatConfig.formatter2.format(now);
        labels.add(label);

        double used = memoryMXBean.getHeapMemoryUsage().getUsed()/1000/1000;
        double committed = memoryMXBean.getHeapMemoryUsage().getCommitted()/1000/1000;
        double max = memoryMXBean.getHeapMemoryUsage().getMax()/1000/1000;

        ChartBean.DataSet memoryUsedDataSet = memoryDataSetMap.get("memoryUsedDataSet");
        ChartBean.DataSet memoryCommitDataSet = memoryDataSetMap.get("memoryCommitDataSet");
        ChartBean.DataSet memoryMaxDataSet = memoryDataSetMap.get("memoryMaxDataSet");
        memoryUsedDataSet.getData().add(used);
        memoryCommitDataSet.getData().add(committed);
        memoryMaxDataSet.getData().add(max);
        jvmMemoryChart.getDatasets().clear();
        jvmMemoryChart.getDatasets().add(memoryUsedDataSet);
        jvmMemoryChart.getDatasets().add(memoryCommitDataSet);
        jvmMemoryChart.getDatasets().add(memoryMaxDataSet);
    }
}
