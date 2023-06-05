package com.company.app.telegram.component.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.Binder;
import com.company.app.telegram.component.api.BinderExecutor;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.company.app.telegram.binder.api.Binder.BINDER_DELIMITER;

@Component
public class BinderExecutorImpl implements BinderExecutor {

    private Map<String, Binder> binders;

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private List<Binder> binderList;

    @PostConstruct
    void init() {
        binders = binderList.stream()
                .collect(Collectors.toMap(Binder::getType, Function.identity()));
        if (binderList.size() != binders.keySet().size()) {
            throw new DuplicateKeyException("ты конечно красиво написал, но дубли не проверил!");
        }
    }

    @EventListener({ContextRefreshedEvent.class})
    void saveSubscriptions() {
        List<Subscription> subscriptionList = binders.keySet().stream()
                .map(binderType -> Subscription.builder().type(binderType).build())
                .collect(Collectors.toList());
        subscriptionRepository.saveAll(subscriptionList);
    }

    @Override
    public void execute(Chat chat, String text) {
        Optional<String> first = Arrays.stream(text.split(BINDER_DELIMITER)).findFirst();

        Binder binder = Optional.ofNullable(binders.get(first.get()))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Не смог вытащить тип binderType из [%s].", text)));

        BinderContainer binderContainer = BinderContainer.builder()
                .chat(chat)
                .message(text)
                .build();

        binder.bind(binderContainer);
    }
}
