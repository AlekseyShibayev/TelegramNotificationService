package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
