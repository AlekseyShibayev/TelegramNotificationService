package com.company.app.exchange_rate;

import com.company.app.exchange_rate.component.ExchangeRateExtractor;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateFacade {

    private final ExchangeRateExtractor exchangeRateExtractor;
    private final ExchangeRateService exchangeRateService;

    public ExchangeRate extract() {
        return exchangeRateExtractor.extract();
    }

    public ExchangeRate getLast() {
        return exchangeRateService.getLast();
    }

}
