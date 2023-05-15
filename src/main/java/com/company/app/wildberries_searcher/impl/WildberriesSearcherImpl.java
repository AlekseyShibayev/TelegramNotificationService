package com.company.app.wildberries_searcher.impl;

import com.company.app.wildberries_desire_lot.component.data.ResponseProducts;
import com.company.app.wildberries_desire_lot.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries_searcher.api.WildberriesSearcher;
import com.company.app.wildberries_searcher.api.WildberriesSearcherExtractor;
import com.company.app.wildberries_searcher.api.WildberriesSearcherFilterer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.util.WildberriesSearcherProductsUrlCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WildberriesSearcherImpl implements WildberriesSearcher {

	@Autowired
	private WildberriesSearcherExtractor wildberriesSearcherExtractor;
	@Autowired
	private WildberriesSearcherFilterer wildberriesSearcherFilterer;

	@Override
	public List<WildberriesLinkDto> search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		List<WildberriesLinkDto> result = new ArrayList<>();

		Arrays.stream(wildberriesSearcherContainer.getSupplier().split(";"))
				.forEach(supplier -> {
					wildberriesSearcherContainer.setSupplier(supplier);
					result.addAll(searchForOneSupplier(wildberriesSearcherContainer));
				});

		return result;
	}

	private List<WildberriesLinkDto> searchForOneSupplier(WildberriesSearcherContainer wildberriesSearcherContainer) {
		String url = WildberriesSearcherProductsUrlCreator.createUrl(wildberriesSearcherContainer);
		List<ResponseProducts> products = wildberriesSearcherExtractor.extractResponseProducts(url);
		List<ResponseProducts> filteredProducts = wildberriesSearcherFilterer.filter(products, wildberriesSearcherContainer);
		return filteredProducts.stream()
				.map(ResponseProducts::to)
				.distinct()
				.collect(Collectors.toList());
	}
}
