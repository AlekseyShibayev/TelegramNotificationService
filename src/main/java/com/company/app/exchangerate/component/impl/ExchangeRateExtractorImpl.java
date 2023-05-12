package com.company.app.exchangerate.component.impl;

import com.company.app.exchangerate.component.api.AliexpressExchangeRateExtractor;
import com.company.app.exchangerate.component.api.ExchangeRateExtractor;
import com.company.app.exchangerate.domain.entity.ExchangeRate;
import com.company.app.exchangerate.domain.service.api.ExchangeRateService;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Setter
public class ExchangeRateExtractorImpl implements ExchangeRateExtractor {

	@Autowired
	private ExchangeRateService exchangeRateService;
	@Autowired
	private AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor;

	@SneakyThrows
	@Override
	public ExchangeRate extract() {
		ExchangeRate exchange = ExchangeRate.builder()
				.aliexpressExchangeRate(aliexpressExchangeRateExtractor.extract())
				.creationDate(new Date())
				.build();
		exchangeRateService.create(exchange);

		return exchange;
	}
}
