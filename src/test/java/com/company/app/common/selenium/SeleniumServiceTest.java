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

    @Test
    void can_load_response_with_desired_url() {
        String url = "https://www.wildberries.ru/catalog/4051518/detail.aspx";

        Request request = new Request().setUrl(url).setPartOfUrl("price-history.json");
        List<Response> byWeb = seleniumService.findByWeb(List.of(request));

        Assertions.assertEquals("https://basket-01.wbbasket.ru/vol40/part4051/4051518/info/price-history.json",
            byWeb.get(0).getFullUrlAtomicReference().get());
    }

}