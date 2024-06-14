package com.example.spring.boot.rabbitmq.demo.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableCaching
@EnableIntegration
@EnableRabbit
@EnableTransactionManagement
public class RootConfig {

    private final String applicationName;

    public RootConfig(
        @Value("${spring.application.name:}") final String applicationName
    ) {
        this.applicationName = applicationName;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(400);
        executor.initialize();
        return executor;
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", applicationName);
    }

    @Bean
    public SimplePropertyValueConnectionNameStrategy cns() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }

}