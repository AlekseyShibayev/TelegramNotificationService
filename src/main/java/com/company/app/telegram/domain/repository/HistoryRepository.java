package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.History;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {

    @Override
    List<History> findAll();
}
