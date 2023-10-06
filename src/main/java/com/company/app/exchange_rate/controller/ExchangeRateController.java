package com.company.app.exchange_rate.controller;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchangeRate")
public class ExchangeRateController {

    private final ExchangeRepository exchangeRepository;

    /**
     * example: http://localhost:8080/exchangeRate/getLast
     */
    @GetMapping(value = "/getLast", produces = "application/json")
    public ResponseEntity<ExchangeRate> getLast() {
        return ResponseEntity.ok(exchangeRepository.findLast());
    }

}
