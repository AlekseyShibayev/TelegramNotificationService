package com.company.app.core;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.client.HttpResponseException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class GetRequestHandler {

    @SneakyThrows
    public String loadHtmlPage(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (httpResponse.statusCode() == 200) {
            return httpResponse.body();
        } else {
            throw new HttpResponseException(httpResponse.statusCode(), "can't load html from [%s]".formatted(url));
        }
    }

}
