package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HistoryRepository extends JpaRepository<History, Long> {

}
