package com.company.app.springboottest.application;

import com.company.app.core.tool.api.CaptchaFighter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.PostConstruct;

@Slf4j
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource("/test.properties")
@SpringBootTest(
        classes = SpringBootTestApplicationConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class SpringBootTestApplicationContext {

    @MockBean
    protected CaptchaFighter captchaFighter;

    @PostConstruct
    void init() {
        log.debug("**********     запущена группа тестов всего приложения     **********");
    }
}
