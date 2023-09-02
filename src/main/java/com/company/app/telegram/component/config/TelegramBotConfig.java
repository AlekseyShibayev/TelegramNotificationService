package com.company.app.telegram.component.config;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

/**
 * документация телеграм-апи:
 * https://core.telegram.org/bots/features#inputs
 */
public interface TelegramBotConfig {

    void write(BotApiMethod<? extends Serializable> botApiMethod);

    String getName();
}
