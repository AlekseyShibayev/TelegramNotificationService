package com.company.app.telegram.component.binder;

import com.company.app.telegram.domain.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static com.company.app.telegram.component.binder.Binder.BINDER_DELIMITER;

/**
 * Запускает, соответствующий типу Binder.
 */
@Service
@RequiredArgsConstructor
public class BinderExecutor {

    private final BinderRegistry binderRegistry;

    public void execute(Chat chat, String text) {
        Optional<String> first = Arrays.stream(text.split(BINDER_DELIMITER)).findFirst();

        Binder binder = Optional.ofNullable(binderRegistry.get(first.get()))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Не смог вытащить тип binderType из [%s].", text)));

        BinderContext binderContainer = BinderContext.builder()
                .chat(chat)
                .message(text)
                .build();

        binder.bind(binderContainer);
    }

}