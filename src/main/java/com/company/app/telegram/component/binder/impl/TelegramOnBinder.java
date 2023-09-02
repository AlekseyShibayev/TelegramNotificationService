package com.company.app.telegram.component.binder.impl;

import com.company.app.telegram.component.ChatActivationService;
import com.company.app.telegram.component.binder.Binder;
import com.company.app.telegram.component.binder.BinderContext;
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
    public void bind(BinderContext binderContainer) {
        Chat chat = binderContainer.getChat();
        chatActivationService.activate(chat);
    }

}
