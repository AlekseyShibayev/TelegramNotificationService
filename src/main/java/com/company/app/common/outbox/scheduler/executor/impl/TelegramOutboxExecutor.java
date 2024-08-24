package com.company.app.common.outbox.scheduler.executor.impl;

import java.util.List;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.domain.repository.OutboxRepository;
import com.company.app.common.outbox.scheduler.executor.OutboxExecutor;
import com.company.app.telegram.config.TelegramBotApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final TelegramBotApi telegramBotApi;

    @Override
    public Target getType() {
        return TYPE;
    }

    @Transactional
    @Override
    public void execute() {
        List<Outbox> all = outboxRepository.findAllByTargetAndStatus(TYPE, Status.NEW);
        all.forEach(this::forOne);
    }

    private void forOne(Outbox outbox) {
        boolean success = sendToTelegram(outbox);

        if (success) {
            outbox.setStatus(Status.SENT);
        } else {
            outbox.setStatus(Status.FAIL);
        }

        outboxRepository.save(outbox);
    }

    private boolean sendToTelegram(Outbox outbox) {
        SendMessage sendMessage;
        try {
            sendMessage = new ObjectMapper().readValue(outbox.getWhat(), SendMessage.class);
        } catch (JsonProcessingException e) {
            log.error("Can not create message to telegram: [{}].", e.getMessage(), e);
            return false;
        }

        try {
            telegramBotApi.write(sendMessage);
            return true;
        } catch (Exception e) {
            log.error("Can not send message to telegram for chat [{}] with message [{}].", sendMessage.getChatId(), sendMessage.getText());
            return false;
        }
    }

}