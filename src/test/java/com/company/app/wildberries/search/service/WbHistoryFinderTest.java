package com.company.app.wildberries.search.service;

import java.util.Optional;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries.search.model.selenium.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class WbHistoryFinderTest extends SpringBootTestApplication {

    @Autowired
    private WbHistoryFinder wbHistoryFinder;

    @Test
    void test_1() {
        Optional<Response> result = wbHistoryFinder.findHistory();
        Assertions.assertNotEquals(result, Optional.empty());
        Assertions.assertNotNull(result.get().getBody());
    }

}