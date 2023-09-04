package com.company.app.core.infrastructure.entitygraphextractor.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class EntityGraphExtractorFinisher {

    private final EntityManager entityManager;
    private final EntityGraphExtractorPreparer entityGraphExtractorPreparer;

    public <E> E extractOne(EntityGraphExtractorContext<E> context) {
        List<E> entities = context.getEntities_();
        Class<E> eClass = context.getClass_();

        EntityGraph<E> preparedEntityGraph = entityGraphExtractorPreparer.getEntityGraph(context, entityManager);

        return entityManager.find(eClass,
                context.getId_(entities.get(0)),
                Collections.singletonMap("javax.persistence.loadgraph", preparedEntityGraph));
    }

    public <E> List<E> extractAll(EntityGraphExtractorContext<E> context) {
        Class<E> eClass = context.getClass_();

        String idName = entityGraphExtractorPreparer.getFieldNameWithId(context);
        Set<Long> ids = entityGraphExtractorPreparer.getIds(context);
        EntityGraph<E> preparedEntityGraph = entityGraphExtractorPreparer.getEntityGraph(context, entityManager);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(eClass);
        Root<E> eRoot = criteriaQuery.from(eClass);
        criteriaQuery.select(eRoot)
                .where(eRoot.get(idName)
                        .in(ids));

        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setHint("javax.persistence.loadgraph", preparedEntityGraph);
        return typedQuery.getResultList();
    }

}
