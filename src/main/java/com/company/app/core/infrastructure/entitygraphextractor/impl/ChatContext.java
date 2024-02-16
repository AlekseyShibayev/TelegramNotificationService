package com.company.app.core.infrastructure.entitygraphextractor.impl;

import com.company.app.core.infrastructure.entitygraphextractor.common.EntityGraphExtractorAbstractContext;
import com.company.app.core.infrastructure.entitygraphextractor.common.EntityGraphExtractorFinisher;
import com.company.app.telegram.domain.entity.Chat;

import java.util.List;

public class ChatContext extends EntityGraphExtractorAbstractContext<Chat> {

    private ChatContext(List<Chat> firsts, EntityGraphExtractorFinisher finisher) {
        this.entities = firsts;
        this.finisher = finisher;
    }

    public static ChatContext of(List<Chat> firsts, EntityGraphExtractorFinisher finisher) {
        return new ChatContext(firsts, finisher);
    }

    @Override
    public Class<Chat> getClass_() {
        return Chat.class;
    }

    @Override
    public Long getId_(Chat chat) {
        return chat.getId();
    }

    public ChatContext withUserInfo() {
        addParameter("userInfo");
        return this;
    }

    public ChatContext withHistories() {
        addParameter("histories");
        return this;
    }

    public ChatContext withSubscriptions() {
        addParameter("subscriptions");
        return this;
    }

    public ChatContext withMode() {
        addParameter("mode");
        return this;
    }

}
