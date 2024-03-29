package com.company.app.telegram.config;

import java.io.Serializable;

import com.company.app.telegram.integration.in.IncomingMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


/**
 * telegram api documentation: https://core.telegram.org/bots/features#inputs
 */
@Slf4j
@Component
@RequiredArgsConstructor()
public class TelegramBotApi extends TelegramLongPollingCommandBot {

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

    public String getName() {
        return this.getBotUsername();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        incomingMessageHandler.take(update);
    }

    @SneakyThrows
    public void write(BotApiMethod<? extends Serializable> botApiMethod) {
        this.execute(botApiMethod);
    }

}