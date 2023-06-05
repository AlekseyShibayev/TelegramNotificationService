package com.company.app.telegram.binder.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.TelegramBinder;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.api.ChatActivationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TelegramOffBinderImpl implements TelegramBinder {

    private static final String TYPE = "TG_OFF";

    @Autowired
    private ChatActivationService chatActivationService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void bind(BinderContainer binderContainer) {
        Chat chat = binderContainer.getChat();
        chatActivationService.deactivate(chat);
    }
}
