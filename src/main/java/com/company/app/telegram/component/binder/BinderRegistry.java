package com.company.app.telegram.component.binder;

import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BinderRegistry {

    private final Map<String, Binder> binderMap = new HashMap<>();

    private final SubscriptionRepository subscriptionRepository;
    private final List<Binder> binderList;

    @PostConstruct
    void initBinders() {
        for (Binder binder : binderList) {
            if (binderMap.containsKey(binder.getType())) {
                throw new DuplicateKeyException("developer! check binders for unique type, current type [%s]".formatted(binder.getType()));
            } else {
                binderMap.put(binder.getType(), binder);
            }
        }
    }

    @EventListener({ContextRefreshedEvent.class})
    void initSubscriptions() {
        List<Subscription> subscriptionList = binderMap.keySet().stream()
                .map(binderType -> Subscription.builder().type(binderType).build())
                .toList();
        subscriptionRepository.saveAll(subscriptionList);
    }

    public Binder get(String binderType) {
        Binder binder = binderMap.get(binderType);
        if (binder != null) {
            return binder;
        } else {
            throw new UnsupportedOperationException("developer! impl this binder type [%s]".formatted(binderType));
        }
    }

}