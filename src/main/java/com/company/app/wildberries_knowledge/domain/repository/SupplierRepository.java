package com.company.app.wildberries_knowledge.domain.repository;

import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {

	@Override
	List<Supplier> findAll();

	Supplier findBySupplierId(String supplierId);
}
