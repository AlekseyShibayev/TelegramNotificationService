package com.company.app.wildberries_knowledge.domain.repository;

import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Supplier findBySupplierId(String supplierId);

}
