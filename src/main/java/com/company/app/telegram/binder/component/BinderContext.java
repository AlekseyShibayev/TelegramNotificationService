package com.company.app.telegram.binder.component;

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
