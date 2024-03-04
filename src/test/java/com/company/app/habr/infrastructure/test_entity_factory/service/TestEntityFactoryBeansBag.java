package com.company.app.habr.infrastructure.test_entity_factory.service;

import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.HabrUserRepository;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Getter
@Component
@RequiredArgsConstructor
public class TestEntityFactoryBeansBag {

    private final TestEntityFactoryFinisher testEntityFactoryFinisher;

    private final SimpleCreator simpleCreator;

    private final HabrRepository habrRepository;
    private final HabrUserRepository habrUserRepository;

}