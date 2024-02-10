package com.company.app.springboottest.application.wildberries_knowledge;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries_knowledge.controller.WildberriesSupplierController;
import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class WildberriesKnowledgeEndToEndTest extends SpringBootTestApplication {

    @Autowired
    private WildberriesSupplierController supplierController;

    @Test
    void supplier_initialRegistry_can_init_test() {
        List<Supplier> supplierList = supplierController.getAll().getBody();

        Assertions.assertNotNull(supplierList);
        Assertions.assertTrue(supplierList.size() > 1);
    }
}
