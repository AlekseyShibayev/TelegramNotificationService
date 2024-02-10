package com.company.app.wildberries_desire.domain.initializer;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries_desire.domain.repository.DesireRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class DesireLotInitializerTest extends SpringBootTestApplication {

    @Autowired
    private DesireRepository desireRepository;
    @Autowired
    private DesireLotInitializer desireInitializer;

    @Test
    void desireLotInitializer_do_init_test() {
        Assertions.assertTrue(desireRepository.findAll().size() > 0);
    }

}