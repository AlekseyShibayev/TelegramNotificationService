package com.company.app.telegram.incoming_message_handler.button.model;

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
