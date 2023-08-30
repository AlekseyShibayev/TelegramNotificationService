package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.infrastructure.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DesireLotInitializerTest extends SpringBootTestApplicationContext {

    @Autowired
    private DesireLotRepository desireLotRepository;
    @Autowired
    private DesireLotInitializer desireLotInitializer;

    @Test
    void desireLotInitializer_do_init_test() {
        Assertions.assertTrue(desireLotRepository.findAll().size() > 0);
    }

}