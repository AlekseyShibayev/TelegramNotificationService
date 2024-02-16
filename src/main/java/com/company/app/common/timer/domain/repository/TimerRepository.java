package com.company.app.common.timer.domain.repository;

import java.util.List;
import java.util.Optional;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.TimerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TimerRepository extends JpaRepository<Timer, Long>, JpaSpecificationExecutor<Timer> {

    Optional<Timer> findByChatNameAndTimerType(String chatName, TimerType timerType);

    List<Timer> findAllByTimerType(TimerType timerType);

}
