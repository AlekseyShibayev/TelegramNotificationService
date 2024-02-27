package com.company.app.configuration;

import com.company.app.common.selenium.model.SeleniumWebDriver;
import com.company.app.common.selenium.service.SeleniumWebDriverCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class SeleniumConfiguration extends SpringBootTestApplication {

    @Autowired
    protected SeleniumWebDriverCreator seleniumWebDriverCreator;
    private SeleniumWebDriver driver;

    @BeforeAll
    void doBeforeAll() {
        driver = seleniumWebDriverCreator.createNew();
        Mockito.when(seleniumWebDriverRegistry.get()).thenReturn(driver);
    }

    @AfterAll
    void doAfterAll() {
        driver.quit();
    }

}