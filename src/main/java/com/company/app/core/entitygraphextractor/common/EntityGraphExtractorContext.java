package com.company.app.core.entitygraphextractor.common;

import java.util.List;

interface EntityGraphExtractorContext<E> {

    Class<E> getClass_();

    Long getId_(E e);

    List<E> getEntities_();

    List<EntityGraphExtractorNode> getNodes_();

    E extractOne();

    List<E> extractAll();

}
