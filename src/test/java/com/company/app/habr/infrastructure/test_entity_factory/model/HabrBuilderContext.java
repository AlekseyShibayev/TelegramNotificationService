package com.company.app.habr.infrastructure.test_entity_factory.model;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory.enrich_inpl.Enrich;
import com.company.app.habr.infrastructure.test_entity_factory.enrich_inpl.HabrUserEnrich;
import com.company.app.habr.infrastructure.test_entity_factory.service.TestEntityFactoryBeansBag;
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
        enrichesChain.add(HabrUserEnrich.of(name));
        return this;
    }

}