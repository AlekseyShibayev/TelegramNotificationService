package com.company.app.core.entitygraphextractor;

import com.company.app.core.entitygraphextractor.common.EntityGraphExtractorFinisher;
import com.company.app.core.entitygraphextractor.impl.ChatContext;
import com.company.app.telegram.domain.entity.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityGraphExtractor {

    private final EntityGraphExtractorFinisher entityGraphExtractorFinisher;

    public ChatContext createContext(Chat chat) {
        return ChatContext.of(Collections.singletonList(chat), entityGraphExtractorFinisher);
    }

    public ChatContext createContext(List<Chat> chats) {
        return ChatContext.of(chats, entityGraphExtractorFinisher);
    }

}
