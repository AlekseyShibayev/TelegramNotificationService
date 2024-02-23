package com.company.app.common.selenium.configuration;

import com.company.app.common.tool.DataExtractorTool;
import com.company.app.core.util.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumWebDriver {

    private static final String PROPERTY = "webdriver.chrome.driver";
    private static final String FIRST_DRIVER_PATH = "chromedriver";
    private static final String PACKAGE_NAME = "driver";
//    private static final String SECOND_DRIVER_PATH = "/usr/local/bin/chromedriver";
//    private static final String SECOND_DRIVER_PATH = "/usr/local/bin/chromedriver";

    @Value("classpath:selenium/chromedriver")
    private Resource resource;

    @Getter
    private ChromeDriver driver;

    private final DataExtractorTool dataExtractorTool;

    @SneakyThrows
    @PostConstruct
    void init() {
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        URL path = loader.getResource(FIRST_DRIVER_PATH);
//        File file = new File(path.getPath());
//        if (file.exists()) {
//            System.setProperty(PROPERTY, path.getPath());
//        } else {
//            System.setProperty(PROPERTY, SECOND_DRIVER_PATH);
//        }

        File driver = new File(PACKAGE_NAME);
        if (driver.exists() && driver.isDirectory()) {
            List<File> files = dataExtractorTool.getFiles(PACKAGE_NAME);
            if (Collections.isNotEmpty(files)) {
                File file = files.get(0);
                System.setProperty(PROPERTY, file.getAbsolutePath());
                log.debug("[{}]", file.getAbsolutePath());
                log.debug("[{}]", file.getPath());
                log.debug("[{}]", file.getCanonicalPath());
            }
        } else {
            URL path = resource.getURL();
            System.setProperty(PROPERTY, path.getPath());
        }

//        InputStream inputStream = resource.getInputStream();
//        File tempFile = File.createTempFile("chromedriver", "");
//        FileUtils.copyInputStreamToFile(inputStream, tempFile);
//        System.setProperty(PROPERTY, tempFile.getPath());

//        URL path = resource.getURL();
//        File file = new File(path.getPath());
//        if (file.exists()) {
//            System.setProperty(PROPERTY, path.getPath());
//        } else {
//
//        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");

        options.addArguments("--silent");
        options.addArguments("--disable-logging");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--log-level=3");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
//        System.setProperty("webdriver.chrome.logfile", "/log/selenium");
//        System.setProperty("webdriver.chrome.verboseLogging", "true");

        this.driver = new ChromeDriver(options);
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        this.driver.manage().deleteAllCookies();
    }

}