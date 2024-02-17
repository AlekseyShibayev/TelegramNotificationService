package com.company.app.common.timer.domain.repository;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.domain.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface TimerRepository extends JpaRepository<Timer, Long>, JpaSpecificationExecutor<Timer> {

    Optional<Timer> findByEntityViewAndActionTypeAndStatusType(String entityView, ActionType actionType, StatusType statusType);

    List<Timer> findAllByActionTypeAndStatusType(ActionType actionType, StatusType statusType);

}
