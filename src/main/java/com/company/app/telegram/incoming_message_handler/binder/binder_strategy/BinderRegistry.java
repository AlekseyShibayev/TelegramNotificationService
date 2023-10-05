package com.company.app.telegram.incoming_message_handler.binder.binder_strategy;

import com.company.app.core.exception.DeveloperMistakeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BinderRegistry {

    private final Map<String, Binder> binderMap = new HashMap<>();
    private final List<Binder> binderList;

    @PostConstruct
    void initBinders() {
        for (Binder binder : binderList) {
            if (binderMap.containsKey(binder.getType())) {
                throw new DeveloperMistakeException("check binders for unique type, current type [%s]".formatted(binder.getType()));
            } else {
                binderMap.put(binder.getType(), binder);
            }
        }
    }

    public Binder get(String binderType) {
        Binder binder = binderMap.get(binderType);
        if (binder != null) {
            return binder;
        } else {
            throw new DeveloperMistakeException("unsupported binder type: [%s]".formatted(binderType));
        }
    }

}