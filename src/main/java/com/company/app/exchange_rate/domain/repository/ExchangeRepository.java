package com.company.app.exchange_rate.domain.repository;

import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository extends CrudRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findFirstByOrderByCreationDateDesc();

    List<ExchangeRate> findAll();

}
