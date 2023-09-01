package com.company.app.exchange_rate.component;

import com.company.app.core.tool.api.DataExtractorTool;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class AliexpressReceiver {
    @Value("${exchangeRate.aliexpressUrl}")
    private String url;

    private static final String FILE_NAME = "exchangerate/aliexpress_mock.json";

    private final DataExtractorTool dataExtractorTool;

    @SneakyThrows
    public String getHtmlResponse() {
        HttpClient client = HttpClient.newHttpClient();
        String mockAsString = dataExtractorTool.getFileAsString(FILE_NAME);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(mockAsString))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        return httpResponse.statusCode() == 200 ? httpResponse.body() : StringUtils.EMPTY;
    }

}
