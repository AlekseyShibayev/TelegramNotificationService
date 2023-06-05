package com.company.app.telegram.component.api;

import com.company.app.telegram.domain.entity.Chat;

/**
 * Запускает, соответствующий типу Binder.
 */
public interface BinderExecutor {

    void execute(Chat chat, String text);
}
