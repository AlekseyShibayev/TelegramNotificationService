package com.company.app.common.selenium.service;

import com.company.app.common.selenium.model.SeleniumWebDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumWebDriverRegistry {

    private final SeleniumWebDriverCreator seleniumWebDriverCreator;

//    private SeleniumWebDriver driver;
//    private SessionId sessionId;
//
//    @PostConstruct
//    private void init() {
//        driver = seleniumWebDriverCreator.createNew();
//        sessionId = driver.getSessionId();
//    }

    public SeleniumWebDriver get() {
        return seleniumWebDriverCreator.createNew();
//
//        if (driver.getSessionId() == null) {
//            log.debug("session id is null");
//            driver.setSessionId(sessionId.toString());
//        }
//        return driver;
    }

}