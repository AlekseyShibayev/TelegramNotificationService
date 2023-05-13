package com.company.app.wildberries.component.searcher.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.wildberries.component.data.Response;
import com.company.app.wildberries.component.data.ResponseProducts;
import com.company.app.wildberries.component.data.Size;
import com.company.app.wildberries.component.searcher.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.WildberriesSearcherUrlCreator;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WildberriesSearcherImpl implements WildberriesSearcher {

	@Autowired
	private JsonSerializationTool<Response> jsonSerializationTool;
	@Autowired
	private TelegramController telegramController;

	@Override
	public List<WildberriesLinkDto> search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		String url = WildberriesSearcherUrlCreator.createUrl(wildberriesSearcherContainer);

		List<ResponseProducts> products = getAllProducts(url);
		return products.stream()
				.filter(responseProducts -> filterOne(responseProducts, wildberriesSearcherContainer.getFootSize()))
				.map(responseProducts -> responseProducts.to())
				.distinct()
				.collect(Collectors.toList());
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
				return result;
			} else {
				result.addAll(products);
				i++;
			}
		}
	}

	private boolean filterOne(ResponseProducts responseProducts, String footSize) {
		List<Size> sizes = responseProducts.getSizes();
		if (sizes.size() != 1) {
			return false;
		}
		Size size = sizes.get(0);
		return size.getName().equals(footSize);
	}

	@SneakyThrows
	public String getHtmlResponse(String url) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		return httpResponse.statusCode() == 200 ? httpResponse.body() : StringUtils.EMPTY;
	}
}
