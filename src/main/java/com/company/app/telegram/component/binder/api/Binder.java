package com.company.app.telegram.component.binder.api;

import com.company.app.telegram.component.binder.BinderContainer;

/**
 * Связывает модуль и входящее сообщение, отправленное пользователем в телеграм бота.
 */
public interface Binder {

    String BINDER_DELIMITER = "::";

    String getType();

    void bind(BinderContainer binderContainer);
}
