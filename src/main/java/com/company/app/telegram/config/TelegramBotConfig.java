package com.company.app.telegram.config;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

/**
 * документация телеграм-апи:
 * https://core.telegram.org/bots/features#inputs
 */
public interface TelegramBotConfig {

    public static final String OWNER = "Owner";
    public static final String ADMIN = "Admin";
    public static final String USER = "User";

    void write(BotApiMethod<? extends Serializable> botApiMethod);

    String getName();
}
