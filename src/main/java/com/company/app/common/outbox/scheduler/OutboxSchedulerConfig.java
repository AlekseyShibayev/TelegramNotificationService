package com.company.app.common.outbox.scheduler;

import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.scheduler.executor.OutboxExecutorsRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "outbox", name = "enable", havingValue = "true")
public class OutboxSchedulerConfig {

    private final OutboxExecutorsRegistry outboxExecutorsRegistry;

    @Scheduled(cron = "${outbox.send.scheduler}")
    @SchedulerLock(name = "outbox.send.scheduler")
    public void sendToTelegram() {
        outboxExecutorsRegistry.get(Target.TELEGRAM).execute();
    }

}