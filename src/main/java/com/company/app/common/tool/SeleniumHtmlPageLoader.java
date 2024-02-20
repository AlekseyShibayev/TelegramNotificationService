package com.company.app.common.tool;

import com.company.app.core.util.Logs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumHtmlPageLoader {

    private static final String FIRST_DRIVER_PATH = "selenium_driver/geckodriver";
    private static final String SECOND_DRIVER_PATH = "/usr/local/bin/geckodriver";

    public String loadHtmlPage(String url) {
        try {
            return loadHtmlPageInner(url);
        } catch (Exception e) {
            Logs.doExceptionLog(log, e, "can't load html from [%s]".formatted(url));
            throw e;
        }
    }

    @SneakyThrows
    private String loadHtmlPageInner(String url) {
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
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

        driver.get(url);

        String pageSource = driver.getPageSource();
        driver.quit();
        return pageSource;
    }

}
