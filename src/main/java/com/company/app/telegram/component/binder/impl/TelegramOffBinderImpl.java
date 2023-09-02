package com.company.app.telegram.component.binder.impl;

import com.company.app.telegram.component.ChatActivationService;
import com.company.app.telegram.component.binder.BinderContainer;
import com.company.app.telegram.component.binder.api.TelegramBinder;
import com.company.app.telegram.domain.entity.Chat;
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
