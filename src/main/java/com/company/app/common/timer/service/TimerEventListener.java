package com.company.app.common.timer.service;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.domain.enums.StatusType;
import com.company.app.common.timer.domain.repository.TimerRepository;
import com.company.app.common.timer.model.StartTimerEvent;
import com.company.app.common.timer.model.StopTimerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TimerEventListener {

    private final TimerRepository timerRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void create(StartTimerEvent event) {
        Timer timer = new Timer()
                .setEntityView(event.getEntityView())
                .setActionType(event.getActionType())
                .setStatusType(StatusType.NEW);
        timerRepository.save(timer);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void reject(StopTimerEvent event) {
        String entityView = event.getEntityView();
        ActionType timerType = event.getActionType();

        Optional<Timer> optional = timerRepository.findByEntityViewAndActionTypeAndStatusType(entityView, timerType, StatusType.NEW);
        optional.ifPresent(timer -> {
            timer.setStatusType(StatusType.REJECT);
            timerRepository.save(timer);
        });
    }

}