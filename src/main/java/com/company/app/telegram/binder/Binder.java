package com.company.app.telegram.binder;

import com.company.app.telegram.binder.component.BinderContext;

/**
 * Связывает модуль и входящее сообщение, отправленное пользователем в телеграм бота.
 */
public interface Binder {

    String BINDER_DELIMITER = "::";

    String getType();

    void bind(BinderContext binderContext);

    default boolean isFirstTimeHere(String incomingMessage) {
        return !incomingMessage.contains(BINDER_DELIMITER);
    }

}
