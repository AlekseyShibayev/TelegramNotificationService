package com.company.app.exchangerate.domain.service.api;

import com.company.app.exchangerate.domain.entity.ExchangeRate;

public interface ExchangeRateService {

	ExchangeRate getLast();

	boolean create(ExchangeRate exchangeRate);
}
