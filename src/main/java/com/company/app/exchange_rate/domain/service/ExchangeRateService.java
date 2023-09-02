package com.company.app.exchange_rate.domain.service;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRepository exchangeRepository;

    public ExchangeRate getLast() {
        return exchangeRepository.findFirstByOrderByCreationDateDesc()
                .orElseThrow(() -> new NoSuchElementException("no one exchange rate"));
    }

}
