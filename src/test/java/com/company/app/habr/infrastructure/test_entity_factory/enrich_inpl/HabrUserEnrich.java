package com.company.app.habr.infrastructure.test_entity_factory.enrich_inpl;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.HabrUser;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.HabrUserRepository;
import com.company.app.habr.infrastructure.test_entity_factory.model.Enrich;
import com.company.app.habr.infrastructure.test_entity_factory.model.HabrBuilderContext;
import com.company.app.habr.infrastructure.test_entity_factory.service.TestEntityFactoryBeansBag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HabrUserEnrich implements Enrich {

    private String name;

    @Override
    public void accept(HabrBuilderContext habrBuilderContext) {
        Habr habr = habrBuilderContext.getHabr();
        TestEntityFactoryBeansBag beansBag = habrBuilderContext.getBeansBag();
        HabrUserRepository habrUserRepository = beansBag.getHabrUserRepository();
        HabrRepository habrRepository = beansBag.getHabrRepository();

        HabrUser user = new HabrUser().setHabr(habr).setName(name);
        habrUserRepository.save(user);
        habr.getHabrUsers().add(user);
        habrRepository.save(habr);
    }

    public static HabrUserEnrich of(String name) {
        return new HabrUserEnrich(name);
    }

}