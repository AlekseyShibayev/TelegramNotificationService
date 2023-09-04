package com.company.app.telegram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
