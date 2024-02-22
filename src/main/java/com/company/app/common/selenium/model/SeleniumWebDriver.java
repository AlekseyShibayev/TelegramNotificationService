package com.company.app.common.selenium.model;

import java.io.File;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class SeleniumWebDriver implements AutoCloseable {

    private static final String FIRST_DRIVER_PATH = "selenium_driver/chromedriver";
    private static final String SECOND_DRIVER_PATH = "/usr/local/bin/chromedriver";

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

    public static SeleniumWebDriver of() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL path = loader.getResource(FIRST_DRIVER_PATH);
        File file = new File(path.getPath());
        if (file.exists()) {
            System.setProperty("webdriver.chrome.driver", path.getPath());
        } else {
            System.setProperty("webdriver.chrome.driver", SECOND_DRIVER_PATH);
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");

        ChromeDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        chromeDriver.manage().deleteAllCookies();

        return new SeleniumWebDriver(chromeDriver);
    }

}