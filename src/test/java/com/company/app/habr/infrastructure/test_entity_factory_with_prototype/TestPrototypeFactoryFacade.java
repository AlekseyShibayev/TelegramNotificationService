package com.company.app.habr.infrastructure.test_entity_factory_with_prototype;

import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.prototype.HabrBuilderPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestPrototypeFactoryFacade {

    private final ApplicationContext applicationContext;

    public HabrBuilderPrototype habrBy(Status status) {
        HabrBuilderPrototype habrBuilderPrototype = this.applicationContext.getBean("habrBuilderPrototype", HabrBuilderPrototype.class);
        return habrBuilderPrototype.setStatus(status);
    }

}