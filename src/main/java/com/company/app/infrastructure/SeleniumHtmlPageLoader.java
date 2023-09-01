package com.company.app.infrastructure;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Component
public class SeleniumHtmlPageLoader {

    private static final String FIRST_DRIVER_PATH = "selenium_driver/geckodriver";
    private static final String SECOND_DRIVER_PATH = "/usr/local/bin/geckodriver";

    public String getHtmlResponse(String urlName) {
        try {
            return getHtmlPage(urlName);
        } catch (InterruptedException e) {
            throw new RuntimeException("Can't get html.");
        }
    }

    private String getHtmlPage(String urlName) throws InterruptedException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL path = loader.getResource(FIRST_DRIVER_PATH);
        File file = new File(path.getPath());
        if (file.exists()) {
            System.setProperty("webdriver.gecko.driver", path.getPath());
        } else {
            System.setProperty("webdriver.gecko.driver", SECOND_DRIVER_PATH);
        }

        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(true);

        WebDriver driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

        driver.get(urlName);

        Thread.sleep(15000);

        String pageSource = driver.getPageSource();
        driver.quit();
        return pageSource;
    }

}
