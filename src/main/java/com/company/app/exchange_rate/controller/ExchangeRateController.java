package com.company.app.exchange_rate.controller;

import com.company.app.exchange_rate.component.ExchangeRateFacade;
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
     * пример запроса: http://localhost:8080/exchangeRate/get
     */
    @GetMapping(value = "/extract", produces = "application/json")
    public ResponseEntity<ExchangeRate> extract() {
        return ResponseEntity.ok(exchangeRateFacade.extract());
    }

    /**
     * пример запроса: http://localhost:8080/exchangeRate/get
     */
    @GetMapping(value = "/getLast", produces = "application/json")
    public ResponseEntity<ExchangeRate> getLast() {
        return ResponseEntity.ok(exchangeRateFacade.getLast());
    }
}
