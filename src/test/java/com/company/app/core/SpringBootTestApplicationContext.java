package com.company.app.core;

import com.company.app.core.infrastructure.entitygraphextractor.EntityGraphExtractor;
import com.company.app.core.temp.tool.api.CaptchaFighter;
import com.company.app.exchange_rate.scheduler.ExchangeRateSchedulerConfig;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.wildberries_desire_lot.scheduler.WildberriesDesireLotSchedulerConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource("/test.properties")
@SpringBootTest(
        classes = SpringBootTestApplicationConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockBean(CaptchaFighter.class)
@MockBean(WildberriesDesireLotSchedulerConfig.class)
@MockBean(ExchangeRateSchedulerConfig.class)
@MockBean(TelegramFacade.class)
public abstract class SpringBootTestApplicationContext {

    @SpyBean
    protected TransactionTemplate transactionTemplate;
    @SpyBean
    protected EntityGraphExtractor entityGraphExtractor;
    @SpyBean
    protected ChatRepository chatRepository;

    @PostConstruct
    void init() {
        log.debug("**********     run spring boot test context     **********");
    }
}
