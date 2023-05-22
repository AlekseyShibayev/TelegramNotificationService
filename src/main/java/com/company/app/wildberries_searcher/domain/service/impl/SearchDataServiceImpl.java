package com.company.app.wildberries_searcher.domain.service.impl;

import com.company.app.wildberries_searcher.domain.entity.SearchData;
import com.company.app.wildberries_searcher.domain.repository.SearchDataRepository;
import com.company.app.wildberries_searcher.domain.service.api.SearchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchDataServiceImpl implements SearchDataService {

	@Autowired
	private SearchDataRepository searchDataRepository;

	@Override
	@Transactional
	public void saveAll(List<SearchData> list) {
		searchDataRepository.saveAll(list);
	}

	@Override
	@Transactional
	public SearchData getSearchData(String chatName) {
		return searchDataRepository.findByChatName(chatName);
	}
}
