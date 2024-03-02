package com.company.app.configuration;

import com.company.app.common.selenium.model.SeleniumWebDriver;
import com.company.app.common.selenium.service.SeleniumWebDriverCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class SeleniumConfiguration extends SpringBootTestApplication {

    @Autowired
    protected SeleniumWebDriverCreator seleniumWebDriverCreator;
    private SeleniumWebDriver driver;

    @BeforeEach
    void doBeforeEach() {
        driver = seleniumWebDriverCreator.createNew();
        Mockito.when(seleniumWebDriverRegistry.get()).thenReturn(driver);
    }

    @AfterEach
    void doAfterEach() {
        driver.quit();
    }

}