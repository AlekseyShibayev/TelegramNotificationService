package com.company.app.telegram.domain.service;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import com.company.app.telegram.domain.spec.ChatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
        Optional<Chat> chat = chatRepository.findOne(Specification.where(ChatSpecification.chatNameIs(chatName)));
        return chat.orElseGet(() -> createChat(chatName));
    }

}