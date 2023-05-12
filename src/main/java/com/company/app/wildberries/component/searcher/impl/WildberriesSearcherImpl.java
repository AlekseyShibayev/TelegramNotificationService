package com.company.app.wildberries.component.searcher.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.wildberries.component.data.Response;
import com.company.app.wildberries.component.data.ResponseProducts;
import com.company.app.wildberries.component.data.Size;
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

//	private static final String URL = "https://catalog.wb.ru/catalog/men_shoes/catalog?appType=1&cat=8194&curr=rub&dest=-3827418&fbrand=671&fsize=40350&page=%s&regions=80,64,38,4,115,83,33,68,30,86,40,1,66,48,110,31,22,114&sort=popular&spp=22&xsubject=105";
	private static final String URL = "https://catalog.wb.ru/catalog/men_shoes/catalog?appType=1&cat=8194&curr=rub&dest=-3827418&fbrand=21;61;671;777;1616;1707;604002;310577555&page=%s&regions=80,64,38,4,115,83,33,68,30,86,40,1,66,48,110,31,22,114&sort=popular&spp=22";
//	private static final String URL = "https://catalog.wb.ru/catalog/women_shoes2/catalog?appType=1&cat=8182&curr=rub&dest=-3827418&fbrand=671&fsize=35440&page=%s&regions=80,64,38,4,115,83,33,68,30,86,40,1,66,48,110,31,22,114&sort=popular&spp=22";
	private static final String SIZE = "45";
//	private static final String SIZE = "40";

	@Autowired
	private JsonSerializationTool<Response> jsonSerializationTool;
	@Autowired
	private TelegramController telegramController;

	@Override
	public List<WildberriesLinkDto> search() {
		List<ResponseProducts> products = getAllProducts();
		return products.stream()
				.filter(this::filterOne)
				.map(ResponseProducts::to)
				.collect(Collectors.toList());
	}

	private List<ResponseProducts> getAllProducts() {
		List<ResponseProducts> result = new ArrayList<>();

		int i = 1;
		while (true) {
			String format = String.format(URL, i);
			String htmlResponse = getHtmlResponse(format);
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

	private boolean filterOne(ResponseProducts responseProducts) {
		List<Size> sizes = responseProducts.getSizes();
		if (sizes.size() != 1) {
			return false;
		}
		Size size = sizes.get(0);
		return size.getName().equals(SIZE);
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
