package com.company.app.telegram.component.api;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обрабатывает сообщения, отправленные пользователем в телеграм бота.
 */
public interface IncomingMessageHandler {

	void process(Update update);
}
