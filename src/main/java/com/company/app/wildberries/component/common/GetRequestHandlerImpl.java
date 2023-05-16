package com.company.app.wildberries.component.common;

import com.company.app.core.util.CaptchaFighter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GetRequestHandlerImpl implements GetRequestHandler{

	@SneakyThrows
	public String getResponseBodyAsString(String url) {
		CaptchaFighter.fight(3_000, 10_000);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		return httpResponse.statusCode() == 200 ? httpResponse.body() : StringUtils.EMPTY;
	}
}
