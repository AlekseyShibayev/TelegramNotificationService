package com.company.app.telegram.component.config;

import com.company.app.telegram.component.TelegramDistributionHub;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.Serializable;

@Slf4j
@Component
public class TelegramBotConfigImpl extends TelegramLongPollingCommandBot implements TelegramBotConfig {

    @Value("${telegram.name}")
    private String name;
    @Value("${telegram.token}")
    private String token;

    @Autowired
    private TelegramDistributionHub telegramDistributionHub;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
//		this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
        log.info("**********     телеграм бот создан     **********");
//		this.execute(GetUpdates.builder().build()); // пока не ясно о каких updates идет речь
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
        telegramDistributionHub.read(update);
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
