package com.company.app.telegram.component.api;

/**
 * Обрабатывает сообщения, отправленные телеграм ботом.
 */
public interface OutgoingMessageHandler {

	void writeToEveryone(Object message);

	void writeToTargetChat(Long chatId, Object message);
}
