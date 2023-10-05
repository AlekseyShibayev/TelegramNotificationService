package com.company.app.telegram.domain.enums;

import com.company.app.telegram.domain.entity.Chat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModeType {

    ADD_DESIRE("ADD_DESIRE"),
    DEFAULT("DEFAULT");

    private final String type;

    public boolean is(Chat chat) {
        return this.getType().equals(chat.getMode().getType());
    }

}
