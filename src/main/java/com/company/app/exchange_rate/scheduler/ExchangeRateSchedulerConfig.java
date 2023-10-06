package com.company.app.exchange_rate.scheduler;

import com.company.app.exchange_rate.scheduler.executor.ExchangeRateSchedulerExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "exchangeRate", name = "enable", havingValue = "true")
public class ExchangeRateSchedulerConfig {

    private final ExchangeRateSchedulerExecutor exchangeRateSchedulerExecutor;

    @EventListener({ContextRefreshedEvent.class})
    @Scheduled(cron = "${exchangeRate.delay}")
    public void writeExchange() {
        exchangeRateSchedulerExecutor.writeExchange();
    }

}
