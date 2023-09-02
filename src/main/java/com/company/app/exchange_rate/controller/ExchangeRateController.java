package com.company.app.exchange_rate.controller;

import com.company.app.exchange_rate.ExchangeRateFacade;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchangeRate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateFacade exchangeRateFacade;

    /**
     * example: http://localhost:8080/exchangeRate/getLast
     */
    @GetMapping(value = "/getLast", produces = "application/json")
    public ResponseEntity<ExchangeRate> getLast() {
        return ResponseEntity.ok(exchangeRateFacade.getLast());
    }

}
