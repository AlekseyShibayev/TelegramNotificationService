package com.company.app.wildberries.search.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.company.app.wildberries.search.model.selenium.Response;
import com.company.app.wildberries.search.model.selenium.SeleniumWebDriver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v121.network.Network;
import org.openqa.selenium.devtools.v121.network.model.RequestId;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbHistoryFinder {

    private static final String WB_URL = """
                                         https://www.wildberries.ru/catalog/4221055/detail.aspx
                                         """;

    public Optional<Response> findHistory() {
        try {
            return Optional.ofNullable(loadHtmlPageInner(WB_URL));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Response loadHtmlPageInner(String url) {
        try (SeleniumWebDriver seleniumWebDriver = SeleniumWebDriver.of()) {
            ChromeDriver driver = seleniumWebDriver.getDriver();
            driver.navigate().to(url);

            CompletableFuture<Response> future = new CompletableFuture<>();
            future.completeAsync(() -> async(driver));
            return future.get();
        }
    }

    @SneakyThrows
    private Response async(ChromeDriver driver) {
        Response response = new Response();
        String history = "price-history.json";

        try (DevTools devTools = driver.getDevTools()) {
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

            devTools.addListener(Network.requestWillBeSent(), request -> {
                if (request.getRequest().getUrl().contains(history)) {
                    response.setUrl(request.getRequest().getUrl());
                }
            });

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                if (responseReceived.getResponse().getUrl().contains(history)) {
                    response.setRequestId(responseReceived.getRequestId());
                }
            });

            devTools.addListener(Network.loadingFinished(), request -> {
                if (response.isResponseReady(request.getRequestId())) {
                    String body = devTools.send(Network.getResponseBody(response.getRequestId())).getBody();
                    response.setBody(body);
                }
            });

            while (response.getBody() == null) {
                Thread.sleep(1000);
            }

            return response;
        }
    }

}