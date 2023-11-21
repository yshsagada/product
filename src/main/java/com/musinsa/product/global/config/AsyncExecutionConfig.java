package com.musinsa.product.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncExecutionConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor()
    {
        return getExecutor();
    }

    private Executor getExecutor()
    {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(100);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setThreadNamePrefix("async-");
        threadPoolTaskExecutor.afterPropertiesSet();
        return threadPoolTaskExecutor;
    }
}
