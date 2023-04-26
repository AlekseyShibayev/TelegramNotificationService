package com.company.app.exchangerate.component.impl;

import com.company.app.core.tool.api.DataExtractorTool;
import com.company.app.exchangerate.component.api.AliexpressReceiver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class AliexpressReceiverImpl implements AliexpressReceiver {

	private static final String FILE_NAME = "exchangerate/aliexpress_mock.json";
	private static final String URL = "https://aliexpress.ru/aer-jsonapi/bx/recommendations/recommend";

	@Autowired
	DataExtractorTool dataExtractorTool;

	@SneakyThrows
	@Override
	public String getHtmlResponse() {
		HttpClient client = HttpClient.newHttpClient();
		String mockAsString = dataExtractorTool.getFileAsString(FILE_NAME);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL))
				.POST(HttpRequest.BodyPublishers.ofString(mockAsString))
				.build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

		return httpResponse.statusCode() == 200 ? httpResponse.body() : StringUtils.EMPTY;
	}
}
