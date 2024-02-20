package com.company.app.wildberries.search.domain.repository;

import com.company.app.wildberries.search.domain.entity.SearchData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchDataRepository extends JpaRepository<SearchData, Long> {

    SearchData findByChatName(String chatName);

}
