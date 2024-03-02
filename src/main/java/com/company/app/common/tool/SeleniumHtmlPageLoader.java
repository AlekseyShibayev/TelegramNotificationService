package com.company.app.common.tool;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumHtmlPageLoader {

    private static final String FIRST_DRIVER_PATH = "selenium/geckodriver";
    private static final String SECOND_DRIVER_PATH = "/usr/local/bin/geckodriver";

    public Optional<String> loadHtmlPage(String url) {
        try {
            return Optional.ofNullable((url));
        } catch (Exception e) {
            log.error("can't load html from [{}], because: [{}]", url, e.getMessage(), e);
            return Optional.empty();
        }
    }

//    @SneakyThrows
//    private String loadHtmlPageInner(String url) {
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        URL path = loader.getResource(FIRST_DRIVER_PATH);
//        File file = new File(path.getPath());
//        if (file.exists()) {
//            System.setProperty("webdriver.gecko.driver", path.getPath());
//        } else {
//            System.setProperty("webdriver.gecko.driver", SECOND_DRIVER_PATH);
//        }
//
////        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
//        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
//
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.setHeadless(true);
//
//        WebDriver driver = new FirefoxDriver(firefoxOptions);
//        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
//        driver.manage().deleteAllCookies();
//
//        driver.get(url);
//
//        String pageSource = driver.getPageSource();
//        driver.quit();
//        return pageSource;
//    }

}