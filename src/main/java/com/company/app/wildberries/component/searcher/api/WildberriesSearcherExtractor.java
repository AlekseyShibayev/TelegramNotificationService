package com.company.app.wildberries.component.searcher.api;

import com.company.app.wildberries.component.data.ResponseProducts;

import java.util.List;

public interface WildberriesSearcherExtractor {

	List<ResponseProducts> extract(String url);
}
