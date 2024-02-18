package com.company.app.exchange_rate.scheduler.executor;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.enums.ExchangeRateType;
import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.repository.ChatRepository;
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
    private final ChatRepository chatRepository;

    @Transactional
    public void writeExchange() {
        ExchangeRate exchangeRate = extractInner();

        String notification = "Курс aliexpress: %s".formatted(exchangeRate.getValue());

        Chat owner = chatRepository.findOwner();
        if (owner.isEnableNotifications()) {
            telegramFacade.writeToTargetChat(owner.getChatName(),notification);
        }
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