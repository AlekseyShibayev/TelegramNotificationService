package com.company.app.wildberries.domain.repository;

import com.company.app.wildberries.domain.entity.Lot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LotRepository extends CrudRepository<Lot, Long> {

	@Override
	List<Lot> findAll();

	@Override
	void deleteById(Long id);
}
