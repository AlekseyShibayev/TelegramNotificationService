package com.company.app.habr.test;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.HabrUser;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.HabrUserRepository;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import com.company.app.habr.infrastructure.test_entity_factory.TestEntityFactory;
import com.company.app.habr.infrastructure.test_entity_factory.enrich_inpl.HabrUserEnrich;
import com.company.app.habr.infrastructure.test_entity_factory.service.TestEntityFactoryBeansBag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class HabrTest extends SpringBootTestApplication {

    private static final String NAME = "name";

    @Autowired
    private SimpleCreator simpleCreator;
    @Autowired
    private TestEntityFactory testEntityFactory;
    @Autowired
    private HabrRepository habrRepository;
    @Autowired
    private HabrUserRepository habrUserRepository;

    @AfterEach
    void doAfterEach_() {
        super.doAfterEach();
        habrUserRepository.deleteAllInBatch();
        habrRepository.deleteAllInBatch();
    }

    @Test
    void simpleCreator_test() {
        Habr habr = transactionTemplate.execute(status -> {
            Habr save = simpleCreator.createMinimumPosibleHabr(Status.ON);
            simpleCreator.addUser(save, NAME);
            return save;
        });

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertEquals(NAME, habr.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

    @Test
    void testEntityFactory_test_with_lambda() {
        Habr habr = testEntityFactory.habrBy(Status.ON)
            .with(habrBuilderContext -> {
                Habr minimumPosibleHabr = habrBuilderContext.getHabr();
                TestEntityFactoryBeansBag beansBag = habrBuilderContext.getBeansBag();
                HabrUserRepository habrUserRepository = beansBag.getHabrUserRepository();
                HabrRepository habrRepository = beansBag.getHabrRepository();

                HabrUser user = new HabrUser().setHabr(minimumPosibleHabr).setName(NAME);
                habrUserRepository.save(user);
                minimumPosibleHabr.getHabrUsers().add(user);
                habrRepository.save(minimumPosibleHabr);
            })
            .createOne();

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertEquals(NAME, habr.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

    @Test
    void testEntityFactory_test_with_impl_as_impl_factory_method() {
        Habr habr = testEntityFactory.habrBy(Status.ON)
            .with( HabrUserEnrich.of(NAME))
            .createOne();

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertEquals(NAME, habr.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

    @Test
    void testEntityFactory_test_with_impl_as_method() {
        Habr one = testEntityFactory.habrBy(Status.ON)
            .withHabrUser(NAME)
            .createOne();

        Assertions.assertEquals(one.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, one.getHabrUsers().size());
        Assertions.assertNotNull(one.getHabrUsers().get(0).getId());
    }

}