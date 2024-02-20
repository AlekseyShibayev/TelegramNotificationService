package com.company.app.wildberries;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries.knowledge.component.SupplierInitializer;
import com.company.app.wildberries.knowledge.domain.entity.Supplier;
import com.company.app.wildberries.knowledge.domain.repository.SupplierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class SupplierInitializerTest extends SpringBootTestApplication {

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