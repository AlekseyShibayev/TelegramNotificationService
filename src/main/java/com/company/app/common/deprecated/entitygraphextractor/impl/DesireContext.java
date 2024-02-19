package com.company.app.common.deprecated.entitygraphextractor.impl;

import java.util.List;

import com.company.app.common.deprecated.entitygraphextractor.common.EntityGraphExtractorAbstractContext;
import com.company.app.common.deprecated.entitygraphextractor.common.EntityGraphExtractorFinisher;
import com.company.app.wildberries_desire.domain.entity.Desire;


public class DesireContext extends EntityGraphExtractorAbstractContext<Desire> {

    private DesireContext(List<Desire> firsts, EntityGraphExtractorFinisher finisher) {
        this.entities = firsts;
        this.finisher = finisher;
    }

    public static DesireContext of(List<Desire> firsts, EntityGraphExtractorFinisher finisher) {
        return new DesireContext(firsts, finisher);
    }

    @Override
    public Class<Desire> getClass_() {
        return Desire.class;
    }

    @Override
    public Long getId_(Desire desire) {
        return desire.getId();
    }

    public DesireContext withDesireLot() {
        addParameter("desireLot");
        return this;
    }

}
