package com.company.app.wildberries.search.domain.service;

import java.util.List;

import com.company.app.common.tool.json.JsonMapper;
import com.company.app.wildberries.search.domain.entity.SearchData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SearchDataInitializer {

    @Value("classpath:wildberries_searcher/init_search_data.json")
    private Resource resource;

    private final JsonMapper<SearchData> jsonTool;
    private final SearchDataService searchDataService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        List<SearchData> list = jsonTool.toJavaAsList(resource, SearchData.class);
        searchDataService.saveAll(list);
    }

}
