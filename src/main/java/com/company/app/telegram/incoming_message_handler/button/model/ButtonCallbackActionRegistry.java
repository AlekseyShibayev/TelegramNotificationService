package com.company.app.telegram.incoming_message_handler.button.model;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ButtonCallbackActionRegistry {

    private final Map<String, ButtonCallbackAction> map = new HashMap<>();
    private final List<ButtonCallbackAction> list;

    @PostConstruct
    void init() {
        for (ButtonCallbackAction binder : list) {
            if (map.containsKey(binder.getType())) {
                throw new DuplicateKeyException("check for unique type, current type: [%s]".formatted(binder.getType()));
            } else {
                map.put(binder.getType(), binder);
            }
        }
    }

    public ButtonCallbackAction get(String binderType) {
        ButtonCallbackAction binder = map.get(binderType);
        if (binder != null) {
            return binder;
        } else {
            throw new UnsupportedOperationException("unsupported type: [%s]".formatted(binderType));
        }
    }

}