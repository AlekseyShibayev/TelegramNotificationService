package com.company.app.telegram.component.binder;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.company.app.telegram.component.binder.Binder.BINDER_DELIMITER;

/**
 * Запускает, соответствующий типу Binder.
 */
@Service
@RequiredArgsConstructor
public class BinderExecutor {

    private Map<String, Binder> binders; // todo encapsulate in BinderRegistry

    private final SubscriptionRepository subscriptionRepository;
    private final List<Binder> binderList;

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

    public void execute(Chat chat, String text) {
        Optional<String> first = Arrays.stream(text.split(BINDER_DELIMITER)).findFirst();

        Binder binder = Optional.ofNullable(binders.get(first.get()))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Не смог вытащить тип binderType из [%s].", text)));

        BinderContext binderContainer = BinderContext.builder()
                .chat(chat)
                .message(text)
                .build();

        binder.bind(binderContainer);
    }

}