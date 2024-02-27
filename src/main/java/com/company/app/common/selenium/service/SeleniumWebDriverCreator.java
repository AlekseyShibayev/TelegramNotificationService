package com.company.app.common.selenium.service;

import com.company.app.common.selenium.model.SeleniumWebDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Value("${selenium.option.headless}")
    private boolean headless;
    @Value("${selenium.option.incognito}")
    private boolean incognito;

    public SeleniumWebDriver createNew() {
        System.setProperty(DRIVER_PATH, driverPath);

        var options = new ChromeOptions();
        options.setBinary(browserPath);

        options.addArguments("--remote-allow-origins=*");
        //        options.addArguments(" --window-size=1920,1080");

        if (incognito) {
            options.addArguments("--incognito");
        }
        if (headless) {
            options.addArguments("--headless");
        }
        var driver = new SeleniumWebDriver(options);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofDays(1));
        driver.manage().deleteAllCookies();

//        driver.navigate().to("https://www.google.com/");
//        driver.switchTo().newWindow(WindowType.TAB);

        return driver;
    }

}