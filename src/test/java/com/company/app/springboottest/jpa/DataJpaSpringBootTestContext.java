package com.company.app.springboottest.jpa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.PostConstruct;

/**
 * If you are looking to load your full application configuration, but use an embedded database,
 * you should consider @SpringBootTest combined with @AutoConfigureTestDatabase rather than this annotation.
 */
@Slf4j
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource("/test.properties")
@DataJpaTest
public abstract class DataJpaSpringBootTestContext {

    protected static final String SELECT_DELIMITER = "Если ты учел все проблемы hibernate, то после этого сообщения будет только один select:";

    @Autowired
    public TestEntityManager testEntityManager;

    @PostConstruct
    void init() {
        log.debug("**********     запущена группа тестов data jpa     **********");
    }

    protected int getSelectCount(CapturedOutput capture) {
        String[] split = capture.getAll().split(SELECT_DELIMITER);
        String queryMessages = split[1];
        return StringUtils.countMatches(queryMessages, "select");
    }
}
