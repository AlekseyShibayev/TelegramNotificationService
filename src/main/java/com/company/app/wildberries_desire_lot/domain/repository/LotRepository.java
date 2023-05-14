package com.company.app.wildberries_desire_lot.domain.repository;

import com.company.app.wildberries_desire_lot.domain.entity.Lot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LotRepository extends CrudRepository<Lot, Long> {

	@Override
	List<Lot> findAll();

	@Override
	void deleteById(Long id);
}
