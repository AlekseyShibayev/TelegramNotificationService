package com.company.app.wildberries.component.searcher.impl;

import com.company.app.wildberries.component.data.ResponseProducts;
import com.company.app.wildberries.component.data.Size;
import com.company.app.wildberries.component.searcher.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.WildberriesSearcherUrlCreator;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherExtractor;
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

	@Override
	public List<WildberriesLinkDto> search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		String url = WildberriesSearcherUrlCreator.createUrl(wildberriesSearcherContainer);
		List<ResponseProducts> products = wildberriesSearcherExtractor.extract(url);
		return products.stream()
				.filter(responseProducts -> filterOne(responseProducts, wildberriesSearcherContainer.getDressSize()))
				.map(responseProducts -> responseProducts.to())
				.distinct()
				.collect(Collectors.toList());
	}

	private boolean filterOne(ResponseProducts responseProducts, String userSize) {
		String rating = responseProducts.getRating();
		String feedbacks = responseProducts.getFeedbacks();
		if (Integer.parseInt(rating) < 4 || Integer.parseInt(feedbacks) < 10) {
			return false;
		}
		List<Size> sizes = responseProducts.getSizes();
		if (sizes.size() != 1) {
			return false;
		}
		Size size = sizes.get(0);
		return size.getName().equals(userSize);
	}
}
