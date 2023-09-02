package com.company.app.telegram.controller;

import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/telegram/subscription")
public class SubscriptionController {

//    private final SubscriptionRepository subscriptionRepository;
//
//    @GetMapping(value = "/{id}", produces = "application/json")
//    public ResponseEntity<Subscription> read(@PathVariable Long id) {
//        return ResponseEntity.ok(subscriptionRepository.findById(id));
//    }
//
//    @GetMapping(value = "/all", produces = "application/json")
//    public ResponseEntity<Set<Subscription>> readAll() {
//        return ResponseEntity.ok(subscriptionService.getAll());
//    }
//
}
