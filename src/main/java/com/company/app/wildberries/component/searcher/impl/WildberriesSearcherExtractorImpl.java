package com.company.app.wildberries.component.searcher.impl;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.core.tool.impl.JsonSerializationToolImpl;
import com.company.app.wildberries.component.common.GetRequestHandler;
import com.company.app.wildberries.component.common.data.Response;
import com.company.app.wildberries.component.common.data.ResponseProducts;
import com.company.app.wildberries.component.common.data.price_history.PriceHistory;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WildberriesSearcherExtractorImpl implements WildberriesSearcherExtractor {

	@Autowired
	private GetRequestHandler getRequestHandler;
	@Autowired
	private JsonSerializationTool<Response> jsonSerializationTool;

	@PerformanceLogAnnotation
	@Override
	public List<ResponseProducts> extractResponseProducts(String url) {
		return getAllProducts(url);
	}

	@Override
	public List<PriceHistory> extractPriceHistory(String url) {
		String htmlResponse = getRequestHandler.getResponseBodyAsString(url);
		JsonSerializationToolImpl<PriceHistory> priceHistoryJsonSerializationTool = new JsonSerializationToolImpl<>();
		return priceHistoryJsonSerializationTool.load(htmlResponse, PriceHistory.class);
	}

	private List<ResponseProducts> getAllProducts(String url) {
		List<ResponseProducts> result = new ArrayList<>();

		int i = 1;
		while (true) {
			String pageUrl = String.format(url, i);
			String htmlResponse = getRequestHandler.getResponseBodyAsString(pageUrl);
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
}
