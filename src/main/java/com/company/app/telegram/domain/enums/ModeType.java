package com.company.app.telegram.domain.enums;

import com.company.app.telegram.domain.entity.Chat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public enum ModeType {

    ADD_DESIRE, DEFAULT;

    public boolean typeOf(Chat chat) {
        return this.name().equals(chat.getMode().getType());
    }

}
