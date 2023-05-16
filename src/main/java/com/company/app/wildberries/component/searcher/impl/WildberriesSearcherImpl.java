package com.company.app.wildberries.component.searcher.impl;

import com.company.app.wildberries.component.common.data.ResponseProducts;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherExtractor;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherFilterer;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.util.WildberriesSearcherProductsUrlCreator;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		String url = WildberriesSearcherProductsUrlCreator.createUrl(wildberriesSearcherContainer);
		List<ResponseProducts> products = wildberriesSearcherExtractor.extractResponseProducts(url);
		List<ResponseProducts> filteredProducts = wildberriesSearcherFilterer.filter(products, wildberriesSearcherContainer);
		return filteredProducts.stream()
				.map(ResponseProducts::toLinkDto)
				.distinct()
				.collect(Collectors.toList());
	}
}
