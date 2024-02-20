package com.company.app.wildberries;

import java.util.List;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries.knowledge.controller.WildberriesSupplierController;
import com.company.app.wildberries.knowledge.domain.entity.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


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
