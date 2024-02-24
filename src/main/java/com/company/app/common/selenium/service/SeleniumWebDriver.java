package com.company.app.common.selenium.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumWebDriver {

    public static final String DRIVER_PATH = "webdriver.chrome.driver";

    @Getter
    private ChromeDriver driver;

    @PostConstruct
    @SneakyThrows
    public void init() {
//        File file = new File("selenium/chrome/chrome");
//        if (Files.notExists(Paths.get("selenium/chrome/chrome")) {
//
//
//
//        }
//
//        FileUtils.deleteDirectory(new File("selenium"));
//
//        load("https://storage.googleapis.com/chrome-for-testing-public/122.0.6261.69/linux64/chromedriver-linux64.zip");
//        load("https://storage.googleapis.com/chrome-for-testing-public/122.0.6261.69/linux64/chrome-linux64.zip");

        this.driver = createChromeDriver();
    }

    private void load(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        InputStream inputStream = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream()).body();
        File tempZip = new File("temp.zip");
        FileUtils.copyInputStreamToFile(inputStream, tempZip);
        try (ZipFile zipFile = new ZipFile(tempZip)) {
            zipFile.extractAll("selenium/122.0.6261.69");
        }
        FileUtils.forceDelete(tempZip);
    }

    private ChromeDriver createChromeDriver() {
        File chromedriverFile = new File("selenium/chromedriver");
        System.setProperty(DRIVER_PATH, chromedriverFile.getAbsolutePath());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments(" --window-size=1920,1080");
//        options.addArguments("--headless");

        if (Files.exists(Paths.get("selenium/chrome/chrome"))) {
            options.setBinary("selenium/chrome/chrome");
        }

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