package com.company.app.telegram.config;

import com.company.app.telegram.incoming_message_handler.IncomingMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotConfigImpl extends TelegramLongPollingCommandBot implements TelegramBotConfig {

    @Value("${telegram.name}")
    private String name;
    @Value("${telegram.token}")
    private String token;

    private final IncomingMessageHandler incomingMessageHandler;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
        log.info("**********     The telegram bot has been created.     **********");
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        incomingMessageHandler.take(update);
    }

    @SneakyThrows
    @Override
    public void write(BotApiMethod<? extends Serializable> botApiMethod) {
        this.execute(botApiMethod);
    }

    @Override
    public String getName() {
        return this.getBotUsername();
    }

}
