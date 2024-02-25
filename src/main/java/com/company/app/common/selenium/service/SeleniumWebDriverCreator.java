package com.company.app.common.selenium.service;

import com.company.app.common.selenium.model.SeleniumWebDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumWebDriverCreator {

    public static final String DRIVER_PATH = "webdriver.chrome.driver";

    @Value("${selenium.driver.path}")
    private String driverPath;
    @Value("${selenium.browser.path}")
    private String browserPath;

    private ChromeOptions options;

    @PostConstruct
    void init() {
        System.setProperty(DRIVER_PATH, driverPath);

        options = new ChromeOptions();
        options.setBinary(browserPath);

        options.addArguments("--remote-allow-origins=*");
//        options.addArguments(" --window-size=1920,1080");
        options.addArguments("--headless");
    }


    public SeleniumWebDriver createChromeDriver() {
        var chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        chromeDriver.manage().deleteAllCookies();

        return new SeleniumWebDriver(chromeDriver);
    }

}