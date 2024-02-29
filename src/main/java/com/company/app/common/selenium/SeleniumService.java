package com.company.app.common.selenium;

import com.company.app.common.selenium.model.Request;
import com.company.app.common.selenium.model.Response;
import com.company.app.common.selenium.model.SeleniumWebDriver;
import com.company.app.common.selenium.service.SeleniumWebDriverRegistry;
import com.company.app.common.tool.CaptchaFighter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.loader.plan.build.internal.returns.AbstractAnyReference;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumService {

    private final SeleniumWebDriverRegistry seleniumWebDriverRegistry;
    private final CaptchaFighter captchaFighter;

    public List<Response> findByWeb(List<Request> requestList) {
        List<Response> result = new ArrayList<>();
        int position = 1;

        try (SeleniumWebDriver driver = seleniumWebDriverRegistry.get()) {
            for (Request request : requestList) {
                String partOfUrl = request.getPartOfUrl();
                String url = request.getUrl();
                log.debug("try to load page [{}]/[{}], url: [{}]", position, requestList.size(), url);
                try {
                    Response response = loadHtmlPageInner(url, partOfUrl, driver);
                    response.setRequest(request);
                    result.add(response);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    captchaFighter.fight(1500, 5000);
                    position++;
                }
            }
        }

        return result;
    }

    @SneakyThrows
    private Response loadHtmlPageInner(String url, String partOfUrl, SeleniumWebDriver driver) {
            driver.navigate().to(url);

            Response response = new Response();

            try (DevTools devTools = driver.getDevTools()) {
                devTools.createSession();
                devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

                CompletableFuture<Response> future = new CompletableFuture<>();
                future.completeAsync(() -> async(response, partOfUrl, devTools));
                return future.get(20, TimeUnit.SECONDS);
            }
    }

    @SneakyThrows
    private Response async(Response response, String partOfUrl, DevTools devTools) {
        AtomicReference<String> atomicReference = new AtomicReference<>();

        devTools.addListener(Network.requestWillBeSent(), request -> {
            String url = request.getRequest().getUrl();
            if (url.contains(partOfUrl)) {
                atomicReference.set(url);
            }
        });

        while (atomicReference.get() == null) {
            Thread.sleep(1000);
        }

        response.setFullUrl(atomicReference.get());
        return response;
    }

}