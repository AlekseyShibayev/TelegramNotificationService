package com.company.app.telegram.domain.service.api;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Регистрирует чаты, при старте приложения.
 */
public interface InitialChatRegistry {

	void init() throws TelegramApiException;
}
