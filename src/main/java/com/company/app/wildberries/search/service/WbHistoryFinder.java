package com.company.app.wildberries.search.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
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

    public Optional<PriceHistoryResult> findHistory() {
        try {
            return Optional.ofNullable(loadHtmlPageInner(WB_URL));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @SneakyThrows
    private PriceHistoryResult loadHtmlPageInner(String url) {
        try (SeleniumWebDriver seleniumWebDriver = SeleniumWebDriver.of()) {
            ChromeDriver driver = seleniumWebDriver.getDriver();
            driver.navigate().to(url);

            CompletableFuture<PriceHistoryResult> future = new CompletableFuture<>();
            future.completeAsync(() -> async(driver));
            return future.get(15, TimeUnit.SECONDS);
        }
    }

    @SneakyThrows
    private static PriceHistoryResult async(ChromeDriver driver) {
        try (DevTools devTools = driver.getDevTools()) {
            devTools.createSession();

            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

            PriceHistoryResult priceHistoryResult = new PriceHistoryResult();

            devTools.addListener(Network.requestWillBeSent(), request -> {
                String history = "price-history.json";
                if (request.getRequest().getUrl().contains(history)) {
                    priceHistoryResult.setRequestId(request.getRequestId());
                    priceHistoryResult.setUrl(request.getRequest().getUrl());
                }
            });

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                String history = "price-history.json";
                if (responseReceived.getResponse().getUrl().contains(history)) {
                    RequestId requestId = responseReceived.getRequestId();
                    Command<Network.GetResponseBodyResponse> command = Network.getResponseBody(requestId);

                    try {
                        Thread.sleep(5000); //TODO
                        Network.GetResponseBodyResponse send = devTools.send(command);
                        String body = send.getBody();
                        priceHistoryResult.setBody(body);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            Thread.sleep(10000);
            return priceHistoryResult;
        }
    }

}