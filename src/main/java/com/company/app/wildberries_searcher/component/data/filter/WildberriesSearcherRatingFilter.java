package com.company.app.wildberries_searcher.component.data.filter;

import com.company.app.wildberries_desire_lot.component.common.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherRatingFilter implements WildberriesSearcherFilter {

	private static final String TYPE = "rating";

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean isPreFilter() {
		return true;
	}

	@Override
	public boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		return withRating(responseProducts);
	}

	private boolean withRating(ResponseProducts responseProducts) {
		String rating = responseProducts.getRating();
		return Integer.parseInt(rating) >= 4;
	}
}
