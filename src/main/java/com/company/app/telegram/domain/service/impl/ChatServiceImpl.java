package com.company.app.telegram.domain.service.impl;

import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.util.ChatUtil;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Магия @Transactional
 * - при помощи прокси делает банальные begin, rollback, commit. т.е. объединяет действия в рамках одной транзакции.
 * - с учетом проверяемых/не проверяемых исключений. Для проверяемых - commit.
 * - внутри неё сущности находятся в hibernate l1 кеше и видят друг друга, пока сущность держит entityManager
 */
@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	ChatRepository chatRepository;

	@Transactional
	@Override
	public Long create(ChatDto chatDto) {
		Optional<Chat> optional = chatRepository.findFirstByChatName(chatDto.getChatName());
		if (optional.isPresent()) {
			return optional.get().getId();
		} else {
			Chat chat = ChatUtil.of(chatDto);
			return chatRepository.save(chat).getId();
		}
	}

	@Transactional
	@Override
	public Chat read(Long id) {
		Optional<Chat> optional = chatRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new ObjectNotFoundException(id, Chat.class.getName());
		}
	}

	@Transactional
	@Override
	public Boolean update(Long id, ChatDto chatDto) {
		Chat chat = ChatUtil.of(id, chatDto);
		chatRepository.save(chat);
		return true;
	}

	@Transactional
	@Override
	public Boolean update(Chat chat) {
		chatRepository.save(chat);
		return true;
	}

	@Transactional
	@Override
	public Boolean delete(Long id) {
		chatRepository.deleteById(id);
		return true;
	}

	@Transactional
	@Override
	public Chat getChatOrCreateIfNotExist(String chatId) {
		if (chatRepository.existsChatByChatName(chatId)) {
			return chatRepository.findFirstByChatName(chatId).get();
		} else {
			return save(chatId);
		}
	}

	private Chat save(String chatId) {
		Chat chat = Chat.builder().chatName(chatId).build();
		return chatRepository.save(chat);
	}

	@Transactional
	@Override
	public List<Chat> getAll() {
		return chatRepository.findAll();
	}

	@Transactional
	@Override
	public void saveAll(List<Chat> list) {
		chatRepository.saveAll(list);
	}
}
