package com.company.app.common.data_base_garbage_collector.impl;

import com.company.app.common.data_base_garbage_collector.DataBaseGarbageCollector;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxDataBaseGarbageCollector implements DataBaseGarbageCollector {

    private final OutboxRepository outboxRepository;

    @Override
    public void clean() {
        outboxRepository.deleteAllByStatus(Status.SENT.name());
        outboxRepository.deleteAllByStatus(Status.FAIL.name());
    }

}