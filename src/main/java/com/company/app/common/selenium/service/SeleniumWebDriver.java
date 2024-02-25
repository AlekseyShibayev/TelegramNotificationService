package com.company.app.common.selenium.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
public class SeleniumWebDriver {

    public static final String DRIVER_PATH = "webdriver.chrome.driver";

    @Value("${selenium.driver.path}")
    private String driverPath;
    @Value("${selenium.browser.path}")
    private String browserPath;

    @Getter
    private ChromeDriver driver;

    @PostConstruct
    @SneakyThrows
    public void init() {
        this.driver = createChromeDriver();
    }

    private ChromeDriver createChromeDriver() {
        System.setProperty(DRIVER_PATH, driverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments(" --window-size=1920,1080");
        options.addArguments("--headless");

        options.setBinary(browserPath);

//        options.addArguments("--silent");
//        options.addArguments("--disable-logging");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--log-level=3");
//        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

        var chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        chromeDriver.manage().deleteAllCookies();

        return chromeDriver;
    }

}