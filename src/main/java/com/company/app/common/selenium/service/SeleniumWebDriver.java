package com.company.app.common.selenium.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumWebDriver {

    public static final String DRIVER_PATH = "webdriver.chrome.driver";
    private static final String PACKAGE_NAME = "driver";
    private static final String FILE_NAME = "chromedriver";
    private static final String PATH = "driver/chromedriver";

    @Value("classpath:selenium/chromedriver")
    private Resource resource;

    @Getter
    private ChromeDriver chromeDriver;

    @PostConstruct
    @SneakyThrows
    public void init() {
        regDriver();
        chromeDriver = createChromeDriver();
    }

    public static ChromeDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments(" --window-size=1920,1080");
        options.addArguments("--headless");

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

    private void regDriver() throws IOException {
        Path directory = Paths.get(PACKAGE_NAME);
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
            copyDriverFromResources();
        } else {
            try (Stream<Path> walk = Files.walk(directory)) {
                walk.filter(Files::isRegularFile)
                        .filter(path -> path.endsWith(FILE_NAME))
                        .map(Path::toFile)
                        .findFirst()
                        .ifPresentOrElse(file -> System.setProperty(DRIVER_PATH, file.getAbsolutePath())
                                , this::copyDriverFromResources);
            }
        }
    }

    @SneakyThrows
    private void copyDriverFromResources() {
        InputStream inputStream = resource.getInputStream();
        File targetFile = new File(PATH);
        FileUtils.copyInputStreamToFile(inputStream, targetFile);
        System.setProperty(DRIVER_PATH, targetFile.getAbsolutePath());
    }

}