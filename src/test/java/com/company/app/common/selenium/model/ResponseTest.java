package com.company.app.common.selenium.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.devtools.v121.network.model.RequestId;


class ResponseTest {

    @Test
    void test_1() {
        Response response = new Response().setRequestId(new RequestId("123.42"));
        RequestId currentRequestId = new RequestId("123.59");

        Assertions.assertTrue(response.isResponseReady(currentRequestId));
    }

    @Test
    void test_2() {
        Response response = new Response().setRequestId(new RequestId("123.42"));
        RequestId currentRequestId = new RequestId("123.42");

        Assertions.assertTrue(response.isResponseReady(currentRequestId));
    }

    @Test
    void test_3() {
        Response response = new Response().setRequestId(null);
        RequestId currentRequestId = new RequestId("123.42");

        Assertions.assertFalse(response.isResponseReady(currentRequestId));
    }

    @Test
    void test_4() {
        Response response = new Response().setRequestId(new RequestId("123.43"));
        RequestId currentRequestId = new RequestId("123.42");

        Assertions.assertFalse(response.isResponseReady(currentRequestId));
    }

}