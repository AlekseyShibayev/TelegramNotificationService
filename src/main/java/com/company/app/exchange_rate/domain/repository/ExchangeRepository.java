package com.company.app.exchange_rate.domain.repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExchangeRepository extends JpaRepository<ExchangeRate, Long> {

    default ExchangeRate findLast() {
        return this.findFirstByOrderByCreationDateDesc()
            .orElseThrow(() -> new NoSuchElementException("no one exchange rate"));
    }

    Optional<ExchangeRate> findFirstByOrderByCreationDateDesc();

}
