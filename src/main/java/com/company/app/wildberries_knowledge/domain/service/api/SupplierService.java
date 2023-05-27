package com.company.app.wildberries_knowledge.domain.service.api;

import com.company.app.wildberries_knowledge.domain.entity.Supplier;

import java.util.List;

public interface SupplierService {

	void saveAll(List<Supplier> list);

	List<Supplier> getAll();

	Supplier getBySupplierId(String supplierId);
}
