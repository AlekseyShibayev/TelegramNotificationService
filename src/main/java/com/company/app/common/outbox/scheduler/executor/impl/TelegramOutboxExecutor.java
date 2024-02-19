package com.company.app.common.outbox.scheduler.executor.impl;

import java.util.List;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.domain.repository.OutboxRepository;
import com.company.app.common.outbox.scheduler.executor.OutboxExecutor;
import com.company.app.telegram.config.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
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
            SendMessage message = SendMessage.builder().chatId(outbox.getWho()).text(outbox.getWhat()).build();
            telegramBotConfig.write(message);

            outbox.setStatus(Status.SENT);
            outboxRepository.save(outbox);
        }
    }

}