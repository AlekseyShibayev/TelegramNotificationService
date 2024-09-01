package com.company.app.wildberries.desire;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wildberries.desire", name = "enable", havingValue = "true")
public class WildberriesDesireScheduler {

    private final WildberriesDesireSearcher wildberriesDesireSearcher;

    @Scheduled(cron = "${wildberries.desire.cron}")
    public void startSearch() {
        wildberriesDesireSearcher.search();
    }

}
