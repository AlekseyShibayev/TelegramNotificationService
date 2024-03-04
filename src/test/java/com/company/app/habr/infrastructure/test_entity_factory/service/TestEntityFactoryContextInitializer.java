package com.company.app.habr.infrastructure.test_entity_factory.service;

import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory.model.HabrBuilderContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TestEntityFactoryContextInitializer {

    private final TestEntityFactoryFinisher testEntityFactoryFinisher;
    private final TestEntityFactoryBeansBag testEntityFactorySpringBeanBag;

    public HabrBuilderContext init(Status status) {
        return new HabrBuilderContext()
            .setStatus(status)
            .setBeansBag(testEntityFactorySpringBeanBag)
            .setTestEntityFactoryFinisher(testEntityFactoryFinisher);
    }

}