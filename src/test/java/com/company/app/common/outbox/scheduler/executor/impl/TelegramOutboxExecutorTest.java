package com.company.app.common.outbox.scheduler.executor.impl;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.service.OutboxService;
import com.company.app.configuration.SpringBootTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class TelegramOutboxExecutorTest extends SpringBootTestApplication {

    @Autowired
    private OutboxService outboxService;
    @Autowired
    private TelegramOutboxExecutor telegramOutboxExecutor;

    @Test
    void test() {
        Outbox before = outboxService.create("who", "what", Target.TELEGRAM);

        telegramOutboxExecutor.execute();

        Outbox after = outboxRepository.findAll().get(0);
        Assertions.assertEquals(after.getWho(), "who");
        Assertions.assertEquals(after.getStatus(), Status.SENT);
    }

}