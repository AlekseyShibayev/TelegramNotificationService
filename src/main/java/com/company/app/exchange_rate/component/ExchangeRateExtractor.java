package com.company.app.exchange_rate.component;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.util.Logs;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import com.company.app.exchange_rate.domain.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateExtractor {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRepository exchangeRepository;
    private final AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor;

    @PerformanceLogAnnotation
    @Transactional
    public ExchangeRate extract() {
        try {
            return extractInner();
        } catch (Exception e) {
            Logs.doExceptionLog(log, e);
            return null;
        }
    }

    @SneakyThrows
    public ExchangeRate extractInner() {
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .aliexpressExchangeRate(aliexpressExchangeRateExtractor.extract())
                .creationDate(new Date())
                .build();
        return exchangeRepository.save(exchangeRate);
    }

}
