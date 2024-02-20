package com.company.app.common.outbox.scheduler.executor.impl;

import com.company.app.common.outbox.OutboxService;
import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.configuration.SpringBootTestApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


class TelegramOutboxExecutorTest extends SpringBootTestApplication {

    @Autowired
    private OutboxService outboxService;
    @Autowired
    private TelegramOutboxExecutor telegramOutboxExecutor;

    @SneakyThrows
    @Test
    void success_sending_test() {
        outboxService.create("who", new ObjectMapper().writeValueAsString(new SendMessage()), Target.TELEGRAM);

        telegramOutboxExecutor.execute();

        Outbox after = outboxRepository.findAll().get(0);
        Assertions.assertEquals("who", after.getWho());
        Assertions.assertEquals(Status.SENT, after.getStatus());
    }

}