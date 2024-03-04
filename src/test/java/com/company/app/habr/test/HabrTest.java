package com.company.app.habr.test;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class HabrTest extends SpringBootTestApplication {

    @Autowired
    private SimpleCreator simpleCreator;

    @Test
    void simpleCreator_test() {
        Habr habr = transactionTemplate.execute(status -> {
            Habr save = simpleCreator.createMinimumPosibleHabr(Status.ON);
            simpleCreator.addUser(save);
            return save;
        });

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

}