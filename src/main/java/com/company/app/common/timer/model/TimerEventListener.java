package com.company.app.common.timer.model;

import java.util.Optional;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.TimerType;
import com.company.app.common.timer.domain.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Service
@RequiredArgsConstructor
public class TimerEventListener {

    private final TimerRepository timerRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void add(StartTimerEvent event) {
        Timer timer = new Timer()
            .setChatName(event.getChatName())
            .setTimerType(event.getTimerType());
        timerRepository.save(timer);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void remove(StopTimerEvent event) {
        String chatName = event.getChatName();
        TimerType timerType = event.getTimerType();
        Optional<Timer> byChatNameAndTimerType = timerRepository.findByChatNameAndTimerType(chatName, timerType);
        byChatNameAndTimerType.ifPresent(timerRepository::delete);
    }

}