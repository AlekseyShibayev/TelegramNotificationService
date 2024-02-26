package com.company.app.wildberries.search.domain.service;

import com.company.app.common.tool.json.JsonMapper;
import com.company.app.wildberries.search.domain.entity.SearchData;
import com.company.app.wildberries.search.domain.repository.SearchDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SearchDataInitializer {

    @Value("classpath:wildberries_searcher/init_search_data.json")
    private Resource resource;

    private final JsonMapper<SearchData> jsonTool;
    private final SearchDataRepository searchDataRepository;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        List<SearchData> allSearchDataList = jsonTool.toJavaAsList(resource, SearchData.class);

        List<SearchData> newSearchDataList = allSearchDataList.stream()
            .filter(searchData -> searchDataRepository.findByChatName(searchData.getChatName()).isEmpty())
            .collect(Collectors.toList());

        searchDataRepository.saveAll(newSearchDataList);
    }

}