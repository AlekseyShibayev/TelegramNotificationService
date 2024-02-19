package com.company.app.common.data_base_garbage_collector.impl;

import java.util.List;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.model.StartTimerEvent;
import com.company.app.common.timer.model.StopTimerEvent;
import com.company.app.common.timer.service.TimerEventListener;
import com.company.app.configuration.SpringBootTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class TimerDataBaseGarbageCollectorTest extends SpringBootTestApplication {

    @Autowired
    private TimerDataBaseGarbageCollector timerDataBaseGarbageCollector;
    @Autowired
    private TimerEventListener timerEventListener;

    @Test
    void can_clean() {
        timerEventListener.create(new StartTimerEvent().setEntityView("1").setActionType(ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT));
        timerEventListener.create(new StartTimerEvent().setEntityView("2").setActionType(ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT));
        timerEventListener.create(new StartTimerEvent().setEntityView("3").setActionType(ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT));

        timerEventListener.reject(new StopTimerEvent().setEntityView("1").setActionType(ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT));
        timerEventListener.reject(new StopTimerEvent().setEntityView("2").setActionType(ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT));

        transactionTemplate.executeWithoutResult(transactionStatus -> timerDataBaseGarbageCollector.clean());

        List<Timer> all = timerRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }

}