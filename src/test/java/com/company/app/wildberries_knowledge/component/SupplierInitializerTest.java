package com.company.app.wildberries_knowledge.component;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class SupplierInitializerTest extends SpringBootTestApplicationContext {

    @Autowired
    private SupplierInitializer supplierInitialRegistry;
    @Autowired
    private SupplierService supplierService;

    @Test
    void can_init() {
        List<Supplier> suppliers = supplierService.getAll();
        Assertions.assertTrue(suppliers.size() > 0);
    }

}