package com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.service;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.enrich_inpl.Enrich;
import com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.model.HabrBuilderContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactoryFinisher {

    private final SimpleCreator simpleCreator;
    private final HabrRepository habrRepository;

    @Transactional
    public List<Habr> create(HabrBuilderContext context) {
        List<Habr> result = new ArrayList<>();
        for (int i = 0; i < context.getAmount(); i++) {
            result.add(createOne(context));
        }
        log.debug("entity has been created");
        return result;
    }

    private Habr createOne(HabrBuilderContext context) {
        Status status = context.getStatus();
        Habr minimumPosibleHabr = simpleCreator.createMinimumPosibleHabr(status);

        context.setHabr(minimumPosibleHabr);
        List<Enrich> enrichesChain = context.getEnrichesChain();

        enrichesChain.forEach(enrich -> enrich.accept(context));

        return habrRepository.save(context.getHabr());
    }

}