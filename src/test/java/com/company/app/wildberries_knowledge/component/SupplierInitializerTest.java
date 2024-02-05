package com.company.app.wildberries_knowledge.component;

import com.company.app.configuration.SpringBootTestApplicationContext;
import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import com.company.app.wildberries_knowledge.domain.repository.SupplierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class SupplierInitializerTest extends SpringBootTestApplicationContext {

    @Autowired
    private SupplierInitializer supplierInitialRegistry;
    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    void can_init_test() {
        List<Supplier> suppliers = supplierRepository.findAll();
        Assertions.assertTrue(suppliers.size() > 0);
    }

}