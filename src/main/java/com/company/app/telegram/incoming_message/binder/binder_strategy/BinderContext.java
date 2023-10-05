package com.company.app.telegram.incoming_message.binder.binder_strategy;

import com.company.app.telegram.domain.entity.Chat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BinderContext {

    private Chat chat;
    private String message;

}
