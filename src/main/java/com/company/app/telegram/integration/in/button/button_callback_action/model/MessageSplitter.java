package com.company.app.telegram.integration.in.button.button_callback_action.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

import static com.company.app.telegram.integration.in.button.button_callback_action.ButtonCallbackAction.BINDER_DELIMITER;

@Getter
@Setter
@Accessors(chain = true)
public class MessageSplitter {

    private String rightPart;

    public static MessageSplitter of(String message) {
        List<String> list = Arrays.stream(message.split(BINDER_DELIMITER)).toList();
        return new MessageSplitter().setRightPart(list.get(1));
    }

    public boolean isRightPartEqual(String desiredString) {
        return rightPart.equals(desiredString);
    }

}