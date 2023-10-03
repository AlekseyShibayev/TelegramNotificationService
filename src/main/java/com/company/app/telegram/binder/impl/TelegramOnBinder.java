package com.company.app.telegram.binder.impl;

import com.company.app.telegram.component.ChatActivationService;
import com.company.app.telegram.binder.Binder;
import com.company.app.telegram.binder.component.BinderContext;
import com.company.app.telegram.domain.entity.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramOnBinder implements Binder {

    private static final String TYPE = "TG_ON";

    private final ChatActivationService chatActivationService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void bind(BinderContext binderContext) {
        Chat chat = binderContext.getChat();
        chatActivationService.activate(chat);
    }

}
