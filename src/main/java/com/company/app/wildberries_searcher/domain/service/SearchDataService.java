package com.company.app.wildberries_searcher.domain.service;

import com.company.app.wildberries_searcher.domain.entity.SearchData;
import com.company.app.wildberries_searcher.domain.repository.SearchDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchDataService {

    private final SearchDataRepository searchDataRepository;

    public void saveAll(List<SearchData> list) {
        searchDataRepository.saveAll(list);
    }

    public SearchData getSearchData(String chatName) {
        return searchDataRepository.findByChatName(chatName);
    }

}
