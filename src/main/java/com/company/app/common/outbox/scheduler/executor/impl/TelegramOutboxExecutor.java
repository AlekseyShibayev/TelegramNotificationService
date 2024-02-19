package com.company.app.common.outbox.scheduler.executor.impl;

import java.util.List;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.domain.repository.OutboxRepository;
import com.company.app.common.outbox.scheduler.executor.OutboxExecutor;
import com.company.app.telegram.config.TelegramBotConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramOutboxExecutor implements OutboxExecutor {

    public static final Target TYPE = Target.TELEGRAM;

    private final OutboxRepository outboxRepository;
    private final TelegramBotConfig telegramBotConfig;

    @Override
    public Target getType() {
        return TYPE;
    }

    @Transactional
    @Override
    public void execute() {
        List<Outbox> all = outboxRepository.findAllByTargetAndStatus(TYPE, Status.NEW);
        for (Outbox outbox : all) {
            forOne(outbox);
        }
    }

    @SneakyThrows
    private void forOne(Outbox outbox) {
        SendMessage sendMessage = new ObjectMapper().readValue(outbox.getWhat(), SendMessage.class);
        telegramBotConfig.write(sendMessage);

        outbox.setStatus(Status.SENT);
        outboxRepository.save(outbox);
    }

}