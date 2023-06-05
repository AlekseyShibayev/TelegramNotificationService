package com.company.app.exchange_rate.domain.service.api;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;

public interface ExchangeRateService {

    ExchangeRate getLast();

    boolean create(ExchangeRate exchangeRate);
}
