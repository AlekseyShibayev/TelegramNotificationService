package com.company.app.telegram.domain.enums;

import com.company.app.telegram.domain.entity.Chat;


public enum ModeType {

    ADD_DESIRE,
    UPDATE_SEARCH_DATA,
    DEFAULT;

    public boolean typeOf(Chat chat) {
        return this.name().equals(chat.getMode().getType());
    }

}
