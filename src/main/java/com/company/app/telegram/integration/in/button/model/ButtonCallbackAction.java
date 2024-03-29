package com.company.app.telegram.integration.in.button.model;

/**
 * Связывает модуль и входящее сообщение, отправленное пользователем в телеграм бота.
 */
public interface ButtonCallbackAction {

    String BINDER_DELIMITER = "::";

    String getType();

    void doAction(ButtonCallbackActionContext context);

    default boolean isFirstTimeHere(String incomingMessage) {
        return !incomingMessage.contains(BINDER_DELIMITER);
    }

}
