package com.company.app.exchange_rate.domain.repository;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findFirstByOrderByCreationDateDesc();

}
