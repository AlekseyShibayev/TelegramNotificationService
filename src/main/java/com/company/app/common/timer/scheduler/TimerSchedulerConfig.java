package com.company.app.common.timer.scheduler;

import com.company.app.common.timer.domain.enums.TimerType;
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
@ConditionalOnProperty(prefix = "timer", name = "enable", havingValue = "true")
public class TimerSchedulerConfig {

    private final TimerExecutorsRegistry timerExecutorsRegistry;

    @EventListener({ContextRefreshedEvent.class})
    @Scheduled(cron = "${timer.rollback_chat_mode_to_default.scheduler_delay}")
    public void rollbackChatModeToDefault() {
        timerExecutorsRegistry.get(TimerType.ROLLBACK_CHAT_MODE_TO_DEFAULT).execute();
    }

}