package com.company.app.configuration;

import com.company.app.common.selenium.service.SeleniumWebDriver;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.net.URL;


public abstract class SeleniumWebDriverTestConfiguration extends SpringBootTestApplication {


    @Value("classpath:selenium/chromedriver")
    private Resource resource;
    @SneakyThrows
    public ChromeDriver createTestChromeDriver() {
        URL url = resource.getURL();
        System.setProperty(SeleniumWebDriver.DRIVER_PATH, url.getPath());
        return SeleniumWebDriver.createChromeDriver();
    }

    @BeforeEach
    void doBeforeEach() {
        Mockito.when(seleniumWebDriver.getChromeDriver()).thenReturn(createTestChromeDriver());
    }

    @AfterEach
    void doAfterEach() {
        //
    }

}