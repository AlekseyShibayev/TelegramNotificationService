package com.company.app.telegram.domain.service;

import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription read(Long id) {
        return subscriptionRepository.findById(id).get();
    }

    public Set<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

}
