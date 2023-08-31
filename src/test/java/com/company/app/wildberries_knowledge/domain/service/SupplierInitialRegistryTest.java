package com.company.app.wildberries_knowledge.domain.service;

import com.company.app.infrastructure.SpringBootTestApplicationContext;
import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class SupplierInitialRegistryTest extends SpringBootTestApplicationContext {

    @Autowired
    private SupplierInitialRegistry supplierInitialRegistry;
    @Autowired
    private SupplierService supplierService;


    @Test
    void can_init() {
        List<Supplier> all = supplierService.getAll();
        Assumptions.assumeTrue(all.size() > 0);
    }

}