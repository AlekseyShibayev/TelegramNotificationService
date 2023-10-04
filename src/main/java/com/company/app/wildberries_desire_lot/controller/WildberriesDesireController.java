package com.company.app.wildberries_desire_lot.controller;

import com.company.app.wildberries_desire_lot.controller.executor.WildberriesDesireControllerExecutor;
import com.company.app.wildberries_desire_lot.controller.dto.FulfilledDesire;
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

    private final WildberriesDesireControllerExecutor wildberriesDesireControllerExecutor;

    @GetMapping(value = "/getFulfilledDesires", produces = "application/json")
    public ResponseEntity<List<FulfilledDesire>> getFulfilledDesires(@RequestParam String chatName) {
        return ResponseEntity.ok(wildberriesDesireControllerExecutor.getFulfilledDesires(chatName));
    }

}
