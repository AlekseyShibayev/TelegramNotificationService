package com.company.app.telegram.domain.service.api;

import com.company.app.telegram.domain.entity.Subscription;

import java.util.Set;

public interface SubscriptionService {

    Subscription read(Long id);

    Set<Subscription> getAll();
}
