package com.company.app.wildberries_searcher.domain.service;

import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.wildberries_searcher.domain.entity.SearchData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchDataInitializer {

    @Value("classpath:wildberries_searcher/init_search_data.json")
    private Resource resource;

    private final JsonTool<SearchData> jsonTool;
    private final SearchDataService searchDataService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        List<SearchData> list = jsonTool.toJavaAsList(resource, SearchData.class);
        searchDataService.saveAll(list);
    }

}
