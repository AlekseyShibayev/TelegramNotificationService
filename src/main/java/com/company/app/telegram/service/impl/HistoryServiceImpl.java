package com.company.app.telegram.service.impl;

import com.company.app.telegram.entity.Chat;
import com.company.app.telegram.entity.History;
import com.company.app.telegram.repository.HistoryRepository;
import com.company.app.telegram.service.api.ChatService;
import com.company.app.telegram.service.api.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	HistoryRepository historyRepository;
	@Autowired
	ChatService chatService;

	@Transactional
	@Override
	public void save(History history) {
		historyRepository.save(history);
	}

	@Transactional
	@Override
	public void save(String text) {
		historyRepository.save(History.builder()
				.message(text)
				.date(new Date())
				.build());
	}

	@Transactional
	@Override
	public void save(Chat chat, String text) {
		historyRepository.save(History.builder()
				.chat(chat)
				.message(text)
				.date(new Date())
				.build());
	}

	@Transactional
	@Override
	public void save(Long chatId, String text) {
		Chat chat = chatService.getChatOrCreateIfNotExist(chatId);
		historyRepository.save(History.builder()
				.chat(chat)
				.message(text)
				.date(new Date())
				.build());
	}
}
