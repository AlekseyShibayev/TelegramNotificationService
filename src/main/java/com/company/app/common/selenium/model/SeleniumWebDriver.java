package com.company.app.common.selenium.model;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


@RequiredArgsConstructor
public class SeleniumWebDriver extends ChromeDriver implements AutoCloseable {

    public SeleniumWebDriver(ChromeOptions options) {
        super(options);
    }

    @Override
    public void setSessionId(String opaqueKey) {
        super.setSessionId(opaqueKey);
    }

    @Override
    public void close() {
//        super.close();
    }

}