package com.company.app.common.deprecated.entitygraphextractor;

import java.util.Collections;
import java.util.List;

import com.company.app.common.deprecated.entitygraphextractor.common.EntityGraphExtractorFinisher;
import com.company.app.common.deprecated.entitygraphextractor.impl.ChatContext;
import com.company.app.common.deprecated.entitygraphextractor.impl.DesireContext;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_desire.domain.entity.Desire;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * first version of dynamic entity graph
 * use EntityFinder
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Deprecated
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
