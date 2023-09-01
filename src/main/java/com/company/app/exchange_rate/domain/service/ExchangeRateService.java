package com.company.app.exchange_rate.domain.service;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRepository exchangeRepository;

    public ExchangeRate getLast() {
        Optional<ExchangeRate> optional = exchangeRepository.findFirstByOrderByCreationDateDesc();
        return optional.orElseThrow(() -> new NoSuchElementException("Курса еще нет."));
    }

    public boolean create(ExchangeRate exchangeRate) {
        exchangeRepository.save(exchangeRate);
        return true;
    }

}
