package com.company.app.telegram.integration.in.button;

import java.util.Arrays;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackActionContext;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackActionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Запускает, соответствующий типу ButtonCallbackAction.
 */
@Service
@RequiredArgsConstructor
public class ButtonCallbackActionExecutor {

    private final ButtonCallbackActionRegistry registry;

    public void execute(Chat chat, String text) {
        String type = Arrays.stream(text.split(ButtonCallbackAction.BINDER_DELIMITER)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("can not find binder type from [%s]", text)));

        ButtonCallbackActionContext context = new ButtonCallbackActionContext()
            .setChat(chat)
            .setMessage(text);

        ButtonCallbackAction binder = registry.get(type);
        binder.doAction(context);
    }

}