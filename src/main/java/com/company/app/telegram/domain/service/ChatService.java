package com.company.app.telegram.domain.service;

import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Long create(ChatDto chatDto) {
        Optional<Chat> optional = chatRepository.findFirstByChatName(chatDto.getChatName());
        if (optional.isPresent()) {
            return optional.get().getId();
        } else {
            Chat chat = Mapper.of(chatDto);
            return chatRepository.save(chat).getId();
        }
    }

    public Chat read(Long id) {
        Optional<Chat> optional = chatRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ObjectNotFoundException(id, Chat.class.getName());
        }
    }

    public Boolean update(Long id, ChatDto chatDto) {
        Chat chat = Mapper.of(id, chatDto);
        chatRepository.save(chat);
        return true;
    }

    public Boolean update(Chat chat) {
        chatRepository.save(chat);
        return true;
    }

    public Boolean delete(Long id) {
        chatRepository.deleteById(id);
        return true;
    }

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

    public List<Chat> getAll() {
        return chatRepository.findAll();
    }

    public void saveAll(List<Chat> list) {
        chatRepository.saveAll(list);
    }

}