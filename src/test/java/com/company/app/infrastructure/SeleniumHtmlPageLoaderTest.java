package com.company.app.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SeleniumHtmlPageLoaderTest extends SpringBootTestApplicationContext {

    @Autowired
    private SeleniumHtmlPageLoader seleniumHtmlPageLoader;

    @Test
    void test() {
        seleniumHtmlPageLoader.loadHtmlPage("https://aliexpress.ru/item/1005005832268516.html");
    }

}