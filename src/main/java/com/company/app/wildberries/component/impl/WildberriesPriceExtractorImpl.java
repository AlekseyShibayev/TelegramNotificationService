package com.company.app.wildberries.component.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.wildberries.component.api.WildberriesPriceExtractor;
import com.company.app.wildberries.component.data.Response;
import com.company.app.wildberries.component.data.ResponseProducts;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Component
public class WildberriesPriceExtractorImpl implements WildberriesPriceExtractor {

	@Autowired
	private JsonSerializationTool<Response> jsonSerializationTool;

	public String extract(String jsonResponse, String id) {
		Response response = jsonSerializationTool.loadOne(jsonResponse, Response.class);
		List<ResponseProducts> products = response.getData().getProducts();
		return products.stream()
				.filter(responseProducts -> responseProducts.getId().equals(Integer.valueOf(id)))
				.map(responseProducts -> responseProducts.getSalePriceU().toString())
				.findFirst()
				.orElseThrow();
	}
}
