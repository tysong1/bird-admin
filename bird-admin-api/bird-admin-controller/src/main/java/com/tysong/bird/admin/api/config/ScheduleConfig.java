package com.tysong.bird.admin.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private static final int CROESIZE = Runtime.getRuntime().availableProcessors();

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ScheduledExecutorService scheduledExecutors= Executors.newScheduledThreadPool(CROESIZE);
        taskRegistrar.setScheduler(scheduledExecutors);
    }
}
