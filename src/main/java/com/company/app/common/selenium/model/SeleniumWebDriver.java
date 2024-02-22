package com.company.app.common.selenium.model;

import lombok.SneakyThrows;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.io.Resource;

import java.time.Duration;


public class SeleniumWebDriver implements AutoCloseable {

    private final ChromeDriver driver;

    public SeleniumWebDriver(ChromeDriver driver) {
        this.driver = driver;
    }

    public ChromeDriver getDriver() {
        return this.driver;
    }

    @Override
    public void close() {
        driver.close();
    }

    @SneakyThrows
    public static SeleniumWebDriver of(Resource resource) {
        System.setProperty("webdriver.chrome.driver", resource.getFile().getPath());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");

        ChromeDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        chromeDriver.manage().deleteAllCookies();

        return new SeleniumWebDriver(chromeDriver);
    }

}