package com.company.app.core.infrastructure.entitygraphextractor;

import com.company.app.core.infrastructure.entitygraphextractor.common.EntityGraphExtractorFinisher;
import com.company.app.core.infrastructure.entitygraphextractor.impl.ChatContext;
import com.company.app.core.infrastructure.entitygraphextractor.impl.DesireContext;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
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

    public ChatContext createChatContext(Chat chat) {
        return ChatContext.of(Collections.singletonList(chat), entityGraphExtractorFinisher);
    }

    public ChatContext createChatContext(List<Chat> chats) {
        return ChatContext.of(chats, entityGraphExtractorFinisher);
    }

    public DesireContext createDesireContext(Desire desire) {
        return DesireContext.of(Collections.singletonList(desire), entityGraphExtractorFinisher);
    }

    public DesireContext createDesireContext(List<Desire> desires) {
        return DesireContext.of(desires, entityGraphExtractorFinisher);
    }

}
