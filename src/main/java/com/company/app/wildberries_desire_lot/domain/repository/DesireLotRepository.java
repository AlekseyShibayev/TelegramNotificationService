package com.company.app.wildberries_desire_lot.domain.repository;

import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DesireLotRepository extends CrudRepository<DesireLot, Long> {

    @Override
    List<DesireLot> findAll();

    @Override
    void deleteById(Long id);
}
