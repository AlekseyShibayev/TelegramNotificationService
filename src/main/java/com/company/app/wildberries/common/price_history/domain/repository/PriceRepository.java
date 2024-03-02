package com.company.app.wildberries.common.price_history.domain.repository;

import com.company.app.wildberries.common.price_history.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PriceRepository extends JpaRepository<Price, Long> {

}
