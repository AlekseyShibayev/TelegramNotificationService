package com.company.app.common.timer.domain.repository;

import java.util.List;
import java.util.Optional;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.domain.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface TimerRepository extends JpaRepository<Timer, Long>, JpaSpecificationExecutor<Timer> {

    Optional<Timer> findByEntityViewAndActionTypeAndStatusType(String entityView, ActionType actionType, StatusType statusType);

    List<Timer> findAllByActionTypeAndStatusType(ActionType actionType, StatusType statusType);

    @Modifying
    @Query(nativeQuery = true, value = "delete from timer t where t.status_type in :statusTypesForRemoval")
    void deleteAllByStatusTypeIn(List<String> statusTypesForRemoval);

}