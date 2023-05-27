package com.company.app.telegram.domain.service.api;

import com.company.app.telegram.domain.entity.Chat;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface HistoryService {

	void saveHistory(Chat chat, String text);

	void saveHistory(SendMessage sendMessage);
}
