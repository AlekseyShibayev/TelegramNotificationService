package com.company.app.wildberries_searcher.util;

import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WildberriesSearcherProductsUrlCreatorTest {

	@Test
	void have_only_one_formatter_test() {
		WildberriesSearcherContainer container = WildberriesSearcherContainer.builder()
				.gender("male")
				.dressSize("50")
				.build();

		String url = WildberriesSearcherProductsUrlCreator.createUrl(container);

		Assertions.assertEquals(1, StringUtils.countMatches(url, "%s"));
	}


	@Test
	void can_work_with_many_size_test() {
		WildberriesSearcherContainer container = WildberriesSearcherContainer.builder()
				.gender("female")
				.dressSize("46;48")
				.build();

		String url = WildberriesSearcherProductsUrlCreator.createUrl(container);

		Assertions.assertTrue(url.contains("&fsize=41332;43296"));
	}
}