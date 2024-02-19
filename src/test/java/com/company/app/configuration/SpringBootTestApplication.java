package com.company.app.configuration;

import com.company.app.common.entity_finder.EntityFinder;
import com.company.app.common.outbox.domain.repository.OutboxRepository;
import com.company.app.common.timer.domain.repository.TimerRepository;
import com.company.app.core.temp.tool.api.CaptchaFighter;
import com.company.app.exchange_rate.scheduler.ExchangeRateSchedulerConfig;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.config.TelegramBotConfigImpl;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import com.company.app.wildberries_desire.domain.repository.DesireLotRepository;
import com.company.app.wildberries_desire.domain.repository.DesireRepository;
import com.company.app.wildberries_desire.scheduler.WildberriesDesireLotSchedulerConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.annotation.PostConstruct;

@Slf4j
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource("/test.properties")
@SpringBootTest(classes = {DbTestConfiguration.class}
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers(disabledWithoutDocker = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockBean(CaptchaFighter.class)
@MockBean(WildberriesDesireLotSchedulerConfig.class)
@MockBean(ExchangeRateSchedulerConfig.class)
@MockBean(TelegramFacade.class)
public abstract class SpringBootTestApplication {

    @MockBean
    protected TelegramBotConfigImpl telegramBotConfig;
    @Autowired
    protected TransactionTemplate transactionTemplate;
    @Autowired
    protected EntityFinder entityFinder;
    @Autowired
    protected ChatRepository chatRepository;
    @Autowired
    protected DesireLotRepository desireLotRepository;
    @Autowired
    protected DesireRepository desireRepository;
    @Autowired
    protected ModeRepository modeRepository;
    @Autowired
    protected TimerRepository timerRepository;
    @Autowired
    protected OutboxRepository outboxRepository;

    @PostConstruct
    void init() {
        log.debug("**********     run spring boot test context     **********");
    }

    @BeforeEach
    void doBeforeEach() {

    }

    @AfterEach
    void doAfterEach() {
        desireRepository.deleteAllInBatch();
        desireLotRepository.deleteAllInBatch();
        timerRepository.deleteAllInBatch();
        outboxRepository.deleteAllInBatch();
    }

}
