package com.company.app.wildberries_desire_lot.domain.initializer;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DesireInitializerTest extends SpringBootTestApplicationContext {

    @Autowired
    private DesireRepository desireRepository;
    @Autowired
    private DesireInitializer desireInitializer;

    @Test
    void desireLotInitializer_do_init_test() {
        Assertions.assertTrue(desireRepository.findAll().size() > 0);
    }

}