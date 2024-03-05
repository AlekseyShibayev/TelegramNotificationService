package com.company.app.habr.infrastructure.test_entity_factory_with_prototype;

import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.model.HabrPrototype;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.service.TestPrototypeCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactoryWithPrototype {

    private final TestPrototypeCreator testPrototypeCreator;

    public HabrPrototype habrBy(Status status) {
        return testPrototypeCreator.habrBy(status);
    }

}