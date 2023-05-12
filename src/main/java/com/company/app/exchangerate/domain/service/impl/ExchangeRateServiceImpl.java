package com.company.app.exchangerate.domain.service.impl;

import com.company.app.exchangerate.domain.entity.ExchangeRate;
import com.company.app.exchangerate.domain.repository.ExchangeRepository;
import com.company.app.exchangerate.domain.service.api.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	private ExchangeRepository exchangeRepository;

	@Transactional
	@Override
	public ExchangeRate getLast() {
		Optional<ExchangeRate> optional = exchangeRepository.findFirstByOrderByCreationDateDesc();
		return optional.orElseThrow(() -> new NoSuchElementException("Курса еще нет."));
	}

	@Transactional
	@Override
	public boolean create(ExchangeRate exchangeRate) {
		exchangeRepository.save(exchangeRate);
		return true;
	}
}
