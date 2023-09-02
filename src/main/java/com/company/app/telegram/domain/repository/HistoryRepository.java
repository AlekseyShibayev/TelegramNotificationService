package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
