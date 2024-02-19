package com.company.app.common.outbox;

import java.util.List;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.scheduler.executor.impl.TelegramOutboxExecutor;
import com.company.app.configuration.SpringBootTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class OutboxServiceTest extends SpringBootTestApplication {

    @Autowired
    private OutboxService outboxService;
    @Autowired
    private TelegramOutboxExecutor telegramOutboxExecutor;

    @Test
    void create_test() {
        Outbox outbox = outboxService.create("who", "what", Target.TELEGRAM);

        List<Outbox> all = outboxRepository.findAll();

        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals("who", all.get(0).getWho());
    }

}