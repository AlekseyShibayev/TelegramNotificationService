package com.company.app.telegram.domain.service.api;

import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;

import java.util.List;

public interface ChatService {

	Long create(ChatDto chatDto);

	Chat read(Long id);

	Boolean update(Long id, ChatDto chatDto);

	Boolean update(Chat chat);

	Boolean delete(Long id);

	Chat getChatOrCreateIfNotExist(Long chatId);

	List<Chat> getAll();
}
