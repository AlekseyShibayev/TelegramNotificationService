package com.company.app.telegram.component.api;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface TelegramBotConfig {

	void write(BotApiMethod botApiMethod);

	String getName();
}
