package com.company.app.wildberries_searcher.domain.repository;

import com.company.app.wildberries_searcher.domain.entity.SearchData;
import org.springframework.data.repository.CrudRepository;

public interface SearchDataRepository extends CrudRepository<SearchData, Long> {

	SearchData findByChatName(String chatName);
}
