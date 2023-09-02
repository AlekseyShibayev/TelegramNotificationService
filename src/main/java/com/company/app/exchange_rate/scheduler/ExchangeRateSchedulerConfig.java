package com.company.app.exchange_rate.scheduler;

import com.company.app.exchange_rate.ExchangeRateFacade;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.telegram.component.TelegramFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "exchangeRate", name = "enable", havingValue = "true")
public class ExchangeRateSchedulerConfig {

    @Autowired
    private TelegramFacade telegramFacade;
    @Autowired
    private ExchangeRateFacade exchangeRateFacade;

    @Scheduled(fixedDelayString = "${exchangeRate.timeout}")
    public void writeExchange() {
        ExchangeRate exchangeRate = exchangeRateFacade.extract();

        String notification = "exchange rate: ali: [%s]".formatted(exchangeRate.getAliexpressExchangeRate());
        log.debug(notification);

        telegramFacade.writeToEveryone(notification);
    }

}
