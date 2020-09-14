package com.tysong.bird.admin.common.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPoolManager {

    public static ExecutorService service = Executors.newFixedThreadPool(500,
            new BasicThreadFactory.Builder().namingPattern("ThreadPoolManager-task-%d").build());

    public static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
}
