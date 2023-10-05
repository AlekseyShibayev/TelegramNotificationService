package com.company.app.telegram.incoming_message.binder.binder_strategy;

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
