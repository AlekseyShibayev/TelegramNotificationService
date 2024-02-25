package com.company.app.common.selenium;

import com.company.app.common.selenium.model.Response;
import com.company.app.common.selenium.model.SeleniumWebDriver;
import com.company.app.common.selenium.service.SeleniumWebDriverCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.openqa.selenium.devtools.v120.network.model.RequestId;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumService {

    private final SeleniumWebDriverCreator seleniumWebDriverCreator;

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
        try (SeleniumWebDriver seleniumWebDriver = seleniumWebDriverCreator.createChromeDriver()) {
            seleniumWebDriver.navigate().to(url);
            CompletableFuture<Response> future = new CompletableFuture<>();
            future.completeAsync(() -> async(partOfUrl, seleniumWebDriver));
            return future.get(20, TimeUnit.SECONDS);
        }
    }

    @SneakyThrows
    private Response async(String partOfUrl, SeleniumWebDriver seleniumWebDriver) {
        Response response = new Response()
                .setPartOfUrl(partOfUrl);

        try (DevTools devTools = seleniumWebDriver.getDevTools()) {
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

            devTools.addListener(Network.requestWillBeSent(), request -> {
                if (request.getRequest().getUrl().contains(partOfUrl)) {
                    response.setUrl(request.getRequest().getUrl());
                }
            });

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                if (responseReceived.getResponse().getUrl().contains(partOfUrl)) {
                    response.setRequestId(responseReceived.getRequestId());
                }
            });

            devTools.addListener(Network.loadingFinished(), request -> {
                RequestId requestId = request.getRequestId();

                if (response.isReadyToGetBody(requestId)) {
                    String body = devTools.send(Network.getResponseBody(requestId)).getBody();
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