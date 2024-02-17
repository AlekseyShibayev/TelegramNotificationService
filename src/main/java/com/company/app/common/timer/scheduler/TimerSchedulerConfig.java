package com.company.app.common.timer.scheduler;

import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.scheduler.executor.TimerExecutorsRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "timer", name = "enable", havingValue = "true")
public class TimerSchedulerConfig {

    private final TimerExecutorsRegistry timerExecutorsRegistry;

    @Scheduled(cron = "${timer.rollback_chat_mode_to_default.scheduler_delay}")
    @SchedulerLock(name = "rollback_chat_mode_to_default")
    public void rollbackChatModeToDefault() {
        timerExecutorsRegistry.get(ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT).execute();
    }

}