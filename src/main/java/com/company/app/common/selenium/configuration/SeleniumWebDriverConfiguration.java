package com.company.app.common.selenium.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.time.Duration;

//@Slf4j
//@Configuration
//@RequiredArgsConstructor
public class SeleniumWebDriverConfiguration {
//
//    private static final String PROPERTY = "webdriver.chrome.driver";
//    private static final String FIRST_DRIVER_PATH = "selenium/chromedriver";
//    private static final String SECOND_DRIVER_PATH = "/usr/local/bin/chromedriver";
//
//    @Value("classpath:selenium/chromedriver")
//    private Resource resource;
//
//
//    @SneakyThrows
//    @Bean
//    public ChromeDriver chromeDriver() {
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        URL path = loader.getResource(FIRST_DRIVER_PATH);
//        File file = new File(path.getPath());
//        if (file.exists()) {
//            System.setProperty(PROPERTY, path.getPath());
//        } else {
//            System.setProperty(PROPERTY, SECOND_DRIVER_PATH);
//        }
//
////        URL path = resource.getURL();
////        File file = new File(path.getPath());
////        if (file.exists()) {
////            System.setProperty(PROPERTY, path.getPath());
////        }
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless");
//
//        ChromeDriver driver = new ChromeDriver(options);
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
//        driver.manage().deleteAllCookies();
//        return driver;
//    }

}