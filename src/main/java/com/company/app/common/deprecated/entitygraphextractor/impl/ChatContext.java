package com.company.app.common.deprecated.entitygraphextractor.impl;

import java.util.List;

import com.company.app.common.deprecated.entitygraphextractor.common.EntityGraphExtractorAbstractContext;
import com.company.app.common.deprecated.entitygraphextractor.common.EntityGraphExtractorFinisher;
import com.company.app.telegram.domain.entity.Chat;


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
