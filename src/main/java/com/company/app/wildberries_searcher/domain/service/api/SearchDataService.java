package com.company.app.wildberries_searcher.domain.service.api;

import com.company.app.wildberries_searcher.domain.entity.SearchData;

import java.util.List;

public interface SearchDataService {

	void saveAll(List<SearchData> list);

	SearchData getSearchData(String chatName);
}
