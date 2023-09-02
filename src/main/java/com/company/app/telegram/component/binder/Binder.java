package com.company.app.telegram.component.binder;

/**
 * Связывает модуль и входящее сообщение, отправленное пользователем в телеграм бота.
 */
public interface Binder {

    String BINDER_DELIMITER = "::";

    String getType();

    void bind(BinderContext binderContainer);

}
