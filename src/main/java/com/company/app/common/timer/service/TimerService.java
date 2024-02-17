package com.company.app.common.timer.service;

import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.model.StartTimerEvent;
import com.company.app.common.timer.model.StopTimerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TimerService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void start(String entityView, ActionType actionType) {
        applicationEventPublisher.publishEvent(new StartTimerEvent()
                .setEntityView(entityView)
                .setActionType(actionType));
    }

    public void stop(String entityView, ActionType actionType) {
        applicationEventPublisher.publishEvent(new StopTimerEvent()
                .setEntityView(entityView)
                .setActionType(actionType));
    }

}