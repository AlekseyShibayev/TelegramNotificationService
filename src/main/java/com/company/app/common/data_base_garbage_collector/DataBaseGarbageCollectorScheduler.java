package com.company.app.common.data_base_garbage_collector;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseGarbageCollectorScheduler {

    private final List<DataBaseGarbageCollector> collectorList;
    private final TransactionTemplate transactionTemplate;

    @Scheduled(cron = "${garbage.clean.scheduler}")
    @SchedulerLock(name = "garbage.clean.scheduler")
    public void cleanAll() {
        log.info("cleanAll start");
        for (DataBaseGarbageCollector dataBaseGarbageCollector : collectorList) {
            transactionTemplate.executeWithoutResult(transactionStatus ->
                dataBaseGarbageCollector.clean());
        }
        log.info("cleanAll end");
    }

}
