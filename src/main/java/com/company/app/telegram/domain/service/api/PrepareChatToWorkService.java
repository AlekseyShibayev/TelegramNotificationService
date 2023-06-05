package com.company.app.telegram.domain.service.api;

import com.company.app.telegram.domain.entity.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Сервис, подготавливающий чат к работе.
 */
public interface PrepareChatToWorkService {

    Chat getPreparedToWorkChat(Message message, Long chatId);
}
