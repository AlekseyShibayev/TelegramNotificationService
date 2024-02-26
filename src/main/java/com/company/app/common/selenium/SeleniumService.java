package com.company.app.common.selenium;

import com.company.app.common.selenium.model.Response;
import com.company.app.common.selenium.model.SeleniumWebDriver;
import com.company.app.common.selenium.service.SeleniumWebDriverRegistry;
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

    private final SeleniumWebDriverRegistry seleniumWebDriverRegistry;

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
        try (SeleniumWebDriver driver = seleniumWebDriverRegistry.get()) {
            driver.navigate().to(url);

            Response response = new Response()
                    .setPartOfUrl(partOfUrl);

            try (DevTools devTools = driver.getDevTools()) {
                devTools.createSession();
                devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

                CompletableFuture<Response> future = new CompletableFuture<>();
                future.completeAsync(() -> async(partOfUrl, response, devTools));
                return future.get(20, TimeUnit.SECONDS);
            }
        }
    }

    @SneakyThrows
    private Response async(String partOfUrl, Response response, DevTools devTools) {
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