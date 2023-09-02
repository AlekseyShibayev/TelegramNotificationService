package com.company.app.telegram.domain.service;

import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription read(Long id) {
        return subscriptionRepository.findById(id).get();
    }

    public Set<Subscription> getAll() {
        return new HashSet<>(subscriptionRepository.findAll());
    }

}
