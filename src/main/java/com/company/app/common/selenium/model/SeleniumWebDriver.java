package com.company.app.common.selenium.model;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

@RequiredArgsConstructor
public class SeleniumWebDriver implements AutoCloseable {

    private final ChromeDriver chromeDriver;

    @Override
    public void close() {
        chromeDriver.quit();
    }

    public WebDriver.Navigation navigate() {
        return chromeDriver.navigate();
    }

    public DevTools getDevTools() {
        return chromeDriver.getDevTools();
    }

}