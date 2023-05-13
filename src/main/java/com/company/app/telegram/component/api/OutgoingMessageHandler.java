package com.company.app.telegram.component.api;

/**
 * Обрабатывает сообщения, отправленные телеграм ботом пользователю.
 */
public interface OutgoingMessageHandler {

	void sendToEveryone(Object message);

	void sendToTargetChat(Long chatId, Object message);
}
