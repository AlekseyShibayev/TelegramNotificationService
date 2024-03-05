package com.company.app.habr.infrastructure.test_entity_factory_with_prototype.model;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.service.HabrPrototypeService;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.service.TestEntityFactoryWithPrototypeFinisher;
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
public class HabrPrototype {

    private Status status;
    private List<EnrichCallback> chain = new ArrayList<>();
    private Habr habr;
    private int amount;

    @Autowired
    private TestEntityFactoryWithPrototypeFinisher testPrototypeFactoryFinisher;
    @Autowired
    private HabrPrototypeService habrPrototypeService;

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
    public HabrPrototype with(EnrichCallback enrich) {
        chain.add(enrich);
        return this;
    }

    public HabrPrototype withHabrUser(String name) {
        chain.add(habr -> habrPrototypeService.enrichHabrByHabrUserName(habr, name));
        return this;
    }

}