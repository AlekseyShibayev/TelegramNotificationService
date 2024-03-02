package com.company.app.wildberries.search.domain.repository;

import java.util.Optional;

import com.company.app.wildberries.search.domain.entity.SearchData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SearchDataRepository extends JpaRepository<SearchData, Long> {

    Optional<SearchData> findByChatName(String chatName);

}
