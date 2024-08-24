package com.company.app.common.selenium;

import java.util.List;

import com.company.app.common.selenium.model.Request;
import com.company.app.common.selenium.model.Response;
import com.company.app.configuration.SeleniumConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class SeleniumServiceTest extends SeleniumConfiguration {

    @Autowired
    private SeleniumService seleniumService;

//    @Test
    void can_load_response_with_desired_url() {
        List<Response> byWeb = seleniumService.findByWeb(List.of(
            new Request()
                .setUrl("https://www.wildberries.ru/catalog/4051518/detail.aspx")
                .setPartOfUrl("price-history.json"),
            new Request()
                .setUrl("https://www.wildberries.ru/catalog/13194759/detail.aspx")
                .setPartOfUrl("price-history.json"))
        );

        Assertions.assertEquals("https://basket-01.wbbasket.ru/vol40/part4051/4051518/info/price-history.json",
            byWeb.get(0).getFullUrl());
        Assertions.assertEquals("https://basket-01.wbbasket.ru/vol131/part13194/13194759/info/price-history.json",
            byWeb.get(1).getFullUrl());
    }

}