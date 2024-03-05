package com.company.app.habr.infrastructure.test_entity_factory_with_prototype.service;

import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.model.HabrPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestPrototypeCreator {

    private final ApplicationContext applicationContext;

    public HabrPrototype habrBy(Status status) {
        HabrPrototype habrBuilderPrototype = this.applicationContext.getBean("habrPrototype", HabrPrototype.class);
        return habrBuilderPrototype.setStatus(status);
    }

}