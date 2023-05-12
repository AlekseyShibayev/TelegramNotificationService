package com.company.app.telegram.domain.service.api;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.History;

public interface HistoryService {

	void save(History history);

	void save(String text);

	void save(Chat chat, String text);

	void save(Long chatId, String text);
}
