package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.IncomingMessageTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IncomingMessageTaskRepository extends JpaRepository<IncomingMessageTask, Long>, JpaSpecificationExecutor<IncomingMessageTask> {

}
