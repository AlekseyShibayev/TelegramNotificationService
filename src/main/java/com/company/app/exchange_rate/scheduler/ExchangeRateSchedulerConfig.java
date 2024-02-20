package com.company.app.exchange_rate.scheduler;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.exchange_rate.scheduler.executor.ExchangeRateSchedulerExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "exchangeRate", name = "enable", havingValue = "true")
public class ExchangeRateSchedulerConfig {

    private final ExchangeRateSchedulerExecutor exchangeRateSchedulerExecutor;

    @PerformanceLogAnnotation
    @Scheduled(cron = "${exchangeRate.delay}")
    public void writeExchange() {
        exchangeRateSchedulerExecutor.writeExchange();
    }

}