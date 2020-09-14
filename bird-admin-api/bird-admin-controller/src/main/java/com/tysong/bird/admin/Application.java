package com.tysong.bird.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages={"com.tysong.bird.admin.api",
        "com.tysong.bird.admin.service",
        "com.tysong.bird.admin.dao"})
@EnableAsync
@EnableCaching
@EnableScheduling
public class Application {

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }

}
