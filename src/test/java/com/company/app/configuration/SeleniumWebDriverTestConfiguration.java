package com.company.app.configuration;

import com.company.app.common.selenium.configuration.SeleniumWebDriverConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.openqa.selenium.chrome.ChromeDriver;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.net.URL;


@TestConfiguration
public class SeleniumWebDriverTestConfiguration {

    @Value("classpath:selenium/chromedriver")
    private Resource resource;

    @Bean
    @SneakyThrows
    public ChromeDriver chromeDriver() {
        URL url = resource.getURL();
        System.setProperty(SeleniumWebDriverConfiguration.DRIVER_PATH, url.getPath());
        return SeleniumWebDriverConfiguration.createChromeDriver();
    }

}