package com.company.app.wildberries.search.domain.service;

import javax.persistence.EntityNotFoundException;

import com.company.app.wildberries.search.domain.dto.SearchDataUpdate;
import com.company.app.wildberries.search.domain.entity.SearchData;
import com.company.app.wildberries.search.domain.repository.SearchDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchDataService {

    private final SearchDataRepository searchDataRepository;

    public SearchData get(String chatName) {
        return searchDataRepository.findByChatName(chatName)
            .orElseThrow(() -> new EntityNotFoundException("search data not found for chat with name: %s".formatted(chatName)));
    }

    public SearchData update(SearchDataUpdate searchDataUpdate) {
        String chatName = searchDataUpdate.getChatName();
        String greedIndex = searchDataUpdate.getGreedIndex();

        SearchData searchData = searchDataRepository.findByChatName(chatName).orElseThrow();
        searchData.setGreedIndex(greedIndex);
        return searchDataRepository.save(searchData);
    }

}