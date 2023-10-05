package com.company.app.telegram.incoming_message.binder.binders;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message.binder.binder_strategy.Binder;
import com.company.app.telegram.incoming_message.binder.binder_strategy.BinderContext;
import com.company.app.telegram.incoming_message.component.ChatActivationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramOffBinder implements Binder {

    private static final String TYPE = "TG_OFF";

    private final ChatActivationService chatActivationService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void bind(BinderContext binderContext) {
        Chat chat = binderContext.getChat();
        chatActivationService.deactivate(chat);
    }

}
