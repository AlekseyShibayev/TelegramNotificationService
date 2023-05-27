package com.company.app.wildberries_searcher.domain.service.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.wildberries_searcher.domain.entity.SearchData;
import com.company.app.wildberries_searcher.domain.service.api.InitialSearchDataRegistry;
import com.company.app.wildberries_searcher.domain.service.api.SearchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitialSearchDataRegistryImpl implements InitialSearchDataRegistry {

	@Value("classpath:wildberries/init_search_data.json")
	private Resource resource;

	@Autowired
	private JsonSerializationTool<SearchData> jsonSerializationTool;
	@Autowired
	private SearchDataService searchDataService;

	@EventListener({ContextRefreshedEvent.class})
	@Override
	public void init() {
		List<SearchData> list = jsonSerializationTool.load(resource, SearchData.class);
		searchDataService.saveAll(list);
	}
}
