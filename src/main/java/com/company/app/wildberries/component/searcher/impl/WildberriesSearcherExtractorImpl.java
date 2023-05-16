package com.company.app.wildberries.component.searcher.impl;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.core.tool.impl.JsonSerializationToolImpl;
import com.company.app.core.util.CaptchaFighter;
import com.company.app.wildberries.component.desire_lot.data.Response;
import com.company.app.wildberries.component.desire_lot.data.ResponseProducts;
import com.company.app.wildberries.component.desire_lot.data.price_history.PriceHistory;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherExtractor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WildberriesSearcherExtractorImpl implements WildberriesSearcherExtractor {

	@Autowired
	private JsonSerializationTool<Response> jsonSerializationTool;

	@PerformanceLogAnnotation
	@Override
	public List<ResponseProducts> extractResponseProducts(String url) {
		return getAllProducts(url);
	}

	@Override
	public List<PriceHistory> extractPriceHistory(String url) {
		String htmlResponse = getHtmlResponse(url);
		JsonSerializationToolImpl<PriceHistory> priceHistoryJsonSerializationTool = new JsonSerializationToolImpl<>();
		return priceHistoryJsonSerializationTool.load(htmlResponse, PriceHistory.class);
	}

	private List<ResponseProducts> getAllProducts(String url) {
		List<ResponseProducts> result = new ArrayList<>();

		int i = 1;
		while (true) {
			String pageUrl = String.format(url, i);
			String htmlResponse = getHtmlResponse(pageUrl);
			Response response = jsonSerializationTool.loadOne(htmlResponse, Response.class);
			List<ResponseProducts> products = response.getData().getProducts();
			if (products.isEmpty()) {
				log.debug("В ходе поиска было [{}] запросов к ВБ.", i);
				return result;
			} else {
				result.addAll(products);
				i++;
			}
		}
	}

	@SneakyThrows
	private String getHtmlResponse(String url) {
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
