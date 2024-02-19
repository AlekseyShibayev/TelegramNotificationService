package com.company.app.common.outbox.domain.repository;

import java.util.List;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface OutboxRepository extends JpaRepository<Outbox, Long>, JpaSpecificationExecutor<Outbox> {

    List<Outbox> findAllByTargetAndStatus(Target target, Status status);

    List<Outbox> findAllByStatus(Status status);

    @Modifying
    @Query(nativeQuery = true, value = "delete from outbox o where o.status = :status")
    void deleteAllByStatus(String status);

}