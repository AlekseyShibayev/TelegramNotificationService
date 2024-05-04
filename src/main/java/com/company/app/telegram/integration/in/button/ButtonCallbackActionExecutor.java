package com.company.app.telegram.integration.in.button;

import java.util.Arrays;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.integration.in.button.button_callback_action.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.button_callback_action.model.ButtonCallbackActionContext;
import com.company.app.telegram.integration.in.button.button_callback_action.ButtonCallbackActionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Запускает, соответствующий типу ButtonCallbackAction.
 */
@Service
@RequiredArgsConstructor
public class ButtonCallbackActionExecutor {

    private final ButtonCallbackActionRegistry buttonCallbackActionRegistry;

    public void execute(Chat chat, String text) {
        String type = Arrays.stream(text.split(ButtonCallbackAction.BINDER_DELIMITER)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("can not find binder type from [%s]", text)));

        ButtonCallbackActionContext context = new ButtonCallbackActionContext()
            .setChat(chat)
            .setMessage(text);

        ButtonCallbackAction buttonCallbackActionImplementation = buttonCallbackActionRegistry.get(type);
        buttonCallbackActionImplementation.doAction(context);
    }

}