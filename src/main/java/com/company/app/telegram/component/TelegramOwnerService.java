package com.company.app.telegram.component;

import com.company.app.core.infrastructure.entitygraphextractor.EntityGraphExtractor;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramOwnerService {

    private final ChatRepository chatRepository;
    private final EntityGraphExtractor entityGraphExtractor;

    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        List<Chat> chats = chatRepository.findAll();
        entityGraphExtractor.createContext(chats)
                .withUserInfo()
                .extractAll();
        return chats;
    }

}
