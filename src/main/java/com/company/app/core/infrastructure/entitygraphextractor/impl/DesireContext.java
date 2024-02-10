package com.company.app.core.infrastructure.entitygraphextractor.impl;

import com.company.app.core.infrastructure.entitygraphextractor.common.EntityGraphExtractorAbstractContext;
import com.company.app.core.infrastructure.entitygraphextractor.common.EntityGraphExtractorFinisher;
import com.company.app.wildberries_desire.domain.entity.Desire;

import java.util.List;

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
