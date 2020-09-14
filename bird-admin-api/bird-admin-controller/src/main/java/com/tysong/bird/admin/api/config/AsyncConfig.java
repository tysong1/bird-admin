package com.tysong.bird.admin.api.config;

import com.tysong.bird.admin.common.config.ThreadPoolManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        return ThreadPoolManager.service;
    }
}
