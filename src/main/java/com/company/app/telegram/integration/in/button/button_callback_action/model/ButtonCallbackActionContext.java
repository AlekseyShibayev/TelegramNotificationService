package com.company.app.telegram.integration.in.button.button_callback_action.model;

import com.company.app.telegram.domain.entity.Chat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class ButtonCallbackActionContext {

    private Chat chat;
    private String message;

}
