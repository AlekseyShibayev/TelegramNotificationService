package com.company.app.common.outbox.service;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;

    public Outbox create(String who, String what, Target target) {
        Outbox outbox = new Outbox()
            .setWho(who)
            .setWhat(what)
            .setTarget(target)
            .setStatus(Status.NEW);
        return outboxRepository.save(outbox);
    }

    public void update(Long outboxId, Status status) {
        outboxRepository.findById(outboxId)
            .ifPresent(outbox -> {
                outbox.setStatus(status);
                outboxRepository.save(outbox);
            });
    }

}
