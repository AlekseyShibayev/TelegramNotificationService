package com.company.app.exchange_rate.component;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.service.api.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ExchangeRateExtractor {

    private final ExchangeRateService exchangeRateService;
    private final AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor;

    @SneakyThrows
    public ExchangeRate extract() {
        ExchangeRate exchange = ExchangeRate.builder()
                .aliexpressExchangeRate(aliexpressExchangeRateExtractor.extract())
                .creationDate(new Date())
                .build();
        exchangeRateService.create(exchange);

        return exchange;
    }

}
