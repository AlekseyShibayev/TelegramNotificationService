package com.company.app.habr.infrastructure.test_entity_factory_with_prototype.service;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.enrich_inpl.Enrich;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.prototype.HabrBuilderPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestPrototypeFactoryFinisher {

   private final SimpleCreator simpleCreator;
   private final HabrRepository habrRepository;

    @Transactional
    public List<Habr> create(HabrBuilderPrototype context) {
        List<Habr> result = new ArrayList<>();
        for (int i = 0; i < context.getAmount(); i++) {
            result.add(createOne(context));
        }
        log.debug("entity has been created");
        return result;
    }

    private Habr createOne(HabrBuilderPrototype context) {
        Status status = context.getStatus();
        Habr minimumPosibleHabr = simpleCreator.createMinimumPosibleHabr(status);

        List<Enrich> enrichesChain = context.getEnrichesChain();
        enrichesChain.forEach(enrich -> enrich.accept(minimumPosibleHabr));

        return habrRepository.save(minimumPosibleHabr);
    }

}