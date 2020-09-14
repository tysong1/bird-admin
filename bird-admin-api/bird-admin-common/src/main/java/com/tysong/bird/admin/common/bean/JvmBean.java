package com.tysong.bird.admin.common.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JvmBean {
    public String upTime;
    public String startTime;
    public String heapUsed;
    public String systemLoadAverage;
    public ChartBean jvmMemoryChart;
    public ChartBean jvmThreadChart;
    public ChartBean jvmGCChart;
    public ChartBean diskChart;
}
