package com.company.app.common.selenium;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.company.app.common.selenium.model.Response;
import com.company.app.common.selenium.model.SeleniumWebDriver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v121.network.Network;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumService {

    public Optional<Response> findByWeb(String url, String partOfUrl) {
        try {
            return Optional.ofNullable(loadHtmlPageInner(url, partOfUrl));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Response loadHtmlPageInner(String url, String partOfUrl) {
        try (SeleniumWebDriver seleniumWebDriver = SeleniumWebDriver.of()) {
            ChromeDriver driver = seleniumWebDriver.getDriver();
            driver.navigate().to(url);

            CompletableFuture<Response> future = new CompletableFuture<>();
            future.completeAsync(() -> async(driver, partOfUrl));
            return future.get(20, TimeUnit.SECONDS);
        }
    }

    @SneakyThrows
    private Response async(ChromeDriver driver, String partOfUrl) {
        Response response = new Response()
            .setPartOfUrl(partOfUrl);

        try (DevTools devTools = driver.getDevTools()) {
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

            devTools.addListener(Network.requestWillBeSent(), request -> {
                if (request.getRequest().getUrl().contains(response.getPartOfUrl())) {
                    response.setUrl(request.getRequest().getUrl());
                }
            });

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                if (responseReceived.getResponse().getUrl().contains(response.getPartOfUrl())) {
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