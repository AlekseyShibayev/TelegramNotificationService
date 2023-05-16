package com.company.app.springboottest.application.wildberries.searcher;

import com.company.app.springboottest.application.ApplicationSpringBootTestContext;
import com.company.app.wildberries.component.desire_lot.data.ResponseProducts;
import com.company.app.wildberries.component.desire_lot.data.Size;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.impl.WildberriesSearcherExtractorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

class WildberriesSearcherEndToEndTest extends ApplicationSpringBootTestContext {

	@Autowired
	private WildberriesSearcher wildberriesSearcher;
	@MockBean
	private WildberriesSearcherExtractorImpl wildberriesSearcherExtractor;

	@Test
	void searcher_can_search() {
		WildberriesSearcherContainer wildberriesSearcherContainer = WildberriesSearcherContainer.builder()
				.chatName("653606407")
				.gender("female")
				.footSize("45")
				.dressSize("46;48")
				.supplier("any_supplier_name_or_id")
				.greedIndex("1.00")
				.build();

		ResponseProducts mock = ResponseProducts.builder()
				.id(101304613)
				.sizes(List.of(
						Size.builder().name("46").build(),
						Size.builder().name("48").build(),
						Size.builder().name("50").build()
						))
				.rating("5")
				.feedbacks("20")
				.salePriceU(100)
				.build();

		Mockito.when(wildberriesSearcherExtractor.extractResponseProducts(Mockito.anyString())).thenReturn(List.of(mock));
		Mockito.when(wildberriesSearcherExtractor.extractPriceHistory(Mockito.anyString())).thenCallRealMethod();

		List<WildberriesLinkDto> dtoList = wildberriesSearcher.search(wildberriesSearcherContainer);

		Assertions.assertTrue(dtoList.size() > 0);
	}
}
