package com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.model;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.HabrUser;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.HabrUserRepository;
import com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.enrich_inpl.Enrich;
import com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.service.TestEntityFactoryBeansBag;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class HabrBuilderContext {

    private Status status;
    private List<Enrich> enrichesChain = new ArrayList<>();
    private Habr habr;
    private int amount;

    private TestEntityFactoryBeansBag beansBag;

    /**
     * terminal operations
     */
    public Habr createOne() {
        this.amount = 1;
        return beansBag.getTestEntityFactoryFinisher().create(this).get(0);
    }

    public List<Habr> createMany(int amount) {
        this.amount = amount;
        return beansBag.getTestEntityFactoryFinisher().create(this);
    }

    /**
     * intermediate operations
     */
    public HabrBuilderContext with(Enrich enrich) {
        enrichesChain.add(enrich);
        return this;
    }

    public HabrBuilderContext withHabrUser(String name) {
        enrichesChain.add(habrBuilderContext -> {
            TestEntityFactoryBeansBag beansBag = habrBuilderContext.getBeansBag();
            HabrUserRepository habrUserRepository = beansBag.getHabrUserRepository();
            HabrRepository habrRepository = beansBag.getHabrRepository();

            HabrUser user = new HabrUser().setHabr(habr).setName(name);
            habrUserRepository.save(user);
            habr.getHabrUsers().add(user);
            habrRepository.save(habr);
        });
        return this;
    }

}