package com.o2s.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadConfig {
    
	@Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("default_task_executor");
        executor.initialize();
        return executor;
    }
	
	@Bean(name = "scheduledTaskExecutor")
    public ThreadPoolTaskScheduler scheduledTaskExecutor() {
        var executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("scheduled_task_executor");
        executor.initialize();
        return executor;
    }

}
