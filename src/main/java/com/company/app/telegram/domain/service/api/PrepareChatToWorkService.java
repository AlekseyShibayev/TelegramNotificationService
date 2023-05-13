package com.company.app.telegram.domain.service.api;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Сервис, подготавливающий чат к работе.
 */
public interface PrepareChatToWorkService {

	void prepareToWork(Message message, Long chatId);
}
