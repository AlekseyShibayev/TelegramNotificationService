package com.company.app.habr.domain.repository;

import com.company.app.habr.domain.entity.HabrUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HabrUserRepository extends JpaRepository<HabrUser, Long> {
}