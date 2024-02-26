package com.company.app.configuration;

import com.company.app.common.selenium.service.SeleniumWebDriverCreator;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class SeleniumConfiguration extends SpringBootTestApplication {

    @Autowired
    protected SeleniumWebDriverCreator seleniumWebDriverCreator;

    @BeforeEach
    void doBeforeEach() {
        Mockito.when(seleniumWebDriverRegistry.get()).thenReturn(seleniumWebDriverCreator.createNew());
    }

}