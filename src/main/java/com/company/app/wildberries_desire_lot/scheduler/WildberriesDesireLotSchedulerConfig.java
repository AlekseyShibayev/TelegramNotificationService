package com.company.app.wildberries_desire_lot.scheduler;

import com.company.app.wildberries_desire_lot.scheduler.executor.WildberriesDesireLotSchedulerExecutor;
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
@ConditionalOnProperty(prefix = "wildberries.desire-lot", name = "enable", havingValue = "true")
public class WildberriesDesireLotSchedulerConfig {

    private final WildberriesDesireLotSchedulerExecutor wildberriesDesireLotSchedulerExecutor;

    @EventListener({ContextRefreshedEvent.class})
    @Scheduled(cron = "${wildberries.desire-lot.search-delay}")
    public void doDesireLotSearch() {
        wildberriesDesireLotSchedulerExecutor.doDesireLotSearch();
    }

}
