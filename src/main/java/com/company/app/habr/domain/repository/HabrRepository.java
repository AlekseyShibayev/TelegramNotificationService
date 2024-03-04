package com.company.app.habr.domain.repository;

import com.company.app.habr.domain.entity.Habr;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HabrRepository extends JpaRepository<Habr, Long> {
}