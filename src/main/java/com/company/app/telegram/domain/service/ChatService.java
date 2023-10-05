package com.company.app.telegram.domain.service;

import com.company.app.core.infrastructure.entitygraphextractor.EntityGraphExtractor;
import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.mapper.Mapper;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ModeRepository modeRepository;

    public Chat createChat(String chatName) {
        Chat chat = new Chat()
                .setChatName(chatName)
                .setMode(modeRepository.findByType(ModeType.DEFAULT));
        return chatRepository.save(chat);
    }

    public Chat findChatByChatNameOrCreateIfNotExist(String chatName) {
        return chatRepository.findByChatName(chatName)
                .orElse(createChat(chatName));
    }

}