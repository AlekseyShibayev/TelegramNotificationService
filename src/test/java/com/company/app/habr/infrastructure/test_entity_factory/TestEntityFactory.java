package com.company.app.habr.infrastructure.test_entity_factory;

import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory.model.HabrBuilderContext;
import com.company.app.habr.infrastructure.test_entity_factory.service.TestEntityFactoryBeansBag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactory {

    private final TestEntityFactoryBeansBag testEntityFactorySpringBeanBag;

    public HabrBuilderContext habrBy(Status status) {
        return new HabrBuilderContext()
            .setStatus(status)
            .setBeansBag(testEntityFactorySpringBeanBag);
    }

}