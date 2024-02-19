package com.company.app.common.data_base_garbage_collector.impl;

import java.util.List;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.service.OutboxService;
import com.company.app.configuration.SpringBootTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class OutboxDataBaseGarbageCollectorTest extends SpringBootTestApplication {

    @Autowired
    private OutboxDataBaseGarbageCollector outboxDataBaseGarbageCollector;
    @Autowired
    private OutboxService outboxService;

    @Test
    void can_clean_test() {
        Outbox outbox1 = outboxService.create("1", "1", Target.TELEGRAM);
        Outbox outbox2 = outboxService.create("2", "1", Target.TELEGRAM);
        Outbox outbox3 = outboxService.create("3", "1", Target.TELEGRAM);
        Outbox outbox4 = outboxService.create("4", "1", Target.TELEGRAM);

        outboxService.update(outbox2.getId(), Status.SENT);
        outboxService.update(outbox3.getId(), Status.SENT);
        transactionTemplate.executeWithoutResult(transactionStatus -> outboxDataBaseGarbageCollector.clean());

        List<Outbox> all = outboxRepository.findAll();
        Assertions.assertEquals(2,all.size());
    }

}