package com.company.app.wildberries_desire_lot.controller;

import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wildberries")
public class WildberriesDesireController {

    private final DesireRepository desireRepository;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<List<Desire>> get(@RequestParam String chatName) {
        return ResponseEntity.ok(desireRepository.findAll());
    }

}
