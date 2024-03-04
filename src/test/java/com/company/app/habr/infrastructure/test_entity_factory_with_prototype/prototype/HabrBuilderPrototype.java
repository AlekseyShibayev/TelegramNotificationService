package com.company.app.habr.infrastructure.test_entity_factory_with_prototype.prototype;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.enrich_inpl.Enrich;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.service.TestPrototypeCreator;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.service.TestPrototypeFactoryFinisher;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Accessors(chain = true)
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HabrBuilderPrototype {

    private Status status;
    private List<Enrich> enrichesChain = new ArrayList<>();
    private Habr habr;
    private int amount;

    @Autowired
    private TestPrototypeFactoryFinisher testPrototypeFactoryFinisher;
    @Autowired
    private TestPrototypeCreator testPrototypeCreator;

    /**
     * terminal operations
     */
    public Habr createOne() {
        this.amount = 1;
        return testPrototypeFactoryFinisher.create(this).get(0);
    }

    public List<Habr> createMany(int amount) {
        this.amount = amount;
        return testPrototypeFactoryFinisher.create(this);
    }

    /**
     * intermediate operations
     */
    public HabrBuilderPrototype with(Enrich enrich) {
        enrichesChain.add(enrich);
        return this;
    }

    public HabrBuilderPrototype withHabrUser(String name) {
        enrichesChain.add(testPrototypeCreator.withHabrUser(name));
        return this;
    }

}