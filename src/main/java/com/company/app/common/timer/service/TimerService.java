package com.company.app.common.timer.service;

import com.company.app.common.timer.domain.enums.TimerType;
import com.company.app.common.timer.model.StartTimerEvent;
import com.company.app.common.timer.model.StopTimerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TimerService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void start(String chatName, TimerType timerType) {
        applicationEventPublisher.publishEvent(new StartTimerEvent()
            .setChatName(chatName)
            .setTimerType(timerType));
    }

    public void stop(String chatName, TimerType timerType) {
        applicationEventPublisher.publishEvent(new StopTimerEvent()
            .setChatName(chatName)
            .setTimerType(timerType));
    }

}