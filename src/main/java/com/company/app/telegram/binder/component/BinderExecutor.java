package com.company.app.telegram.binder.component;

import com.company.app.telegram.binder.Binder;
import com.company.app.telegram.domain.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Запускает, соответствующий типу Binder.
 */
@Service
@RequiredArgsConstructor
public class BinderExecutor {

    private final BinderRegistry binderRegistry;

    public void execute(Chat chat, String text) {
        String binderType = Arrays.stream(text.split(Binder.BINDER_DELIMITER)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("can not find binder type from [%s]", text)));

        BinderContext context = new BinderContext()
                .setChat(chat)
                .setMessage(text);

        Binder binder = binderRegistry.get(binderType);
        binder.bind(context);
    }

}