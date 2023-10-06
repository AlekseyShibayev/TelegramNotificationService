package com.company.app.exchange_rate.scheduler.executor;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.enums.ExchangeRateType;
import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import com.company.app.telegram.TelegramFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateSchedulerExecutor {

    private final TelegramFacade telegramFacade;
    private final AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor;
    private final ExchangeRepository exchangeRepository;

    @Transactional
    public void writeExchange() {
        ExchangeRate exchangeRate = extractInner();

        String notification = "exchange rate: ali: [%s]".formatted(exchangeRate.getValue());
        log.debug(notification);

        telegramFacade.writeToEveryone(notification);
    }

    private ExchangeRate extractInner() {
        String aliexpressExchangeRate = aliexpressExchangeRateExtractor.extract();

        ExchangeRate exchangeRate = new ExchangeRate()
                .setType(ExchangeRateType.ALIEXPRESS)
                .setValue(aliexpressExchangeRate)
                .setCreationDate(new Date());
        return exchangeRepository.save(exchangeRate);
    }

}
