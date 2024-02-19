package com.company.app.common.deprecated.entitygraphextractor.common;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;


@Repository
public class EntityGraphExtractorPreparer {

    public <E> EntityGraph<E> getEntityGraph(EntityGraphExtractorContext<E> context, EntityManager em) {
        Class<E> eClass = context.getClass_();
        List<EntityGraphExtractorNode> nodes = context.getNodes_();

        EntityGraph<E> entityGraph = em.createEntityGraph(eClass);
        prepareGraph(nodes, entityGraph);

        return entityGraph;
    }

    private <E> void prepareGraph(List<EntityGraphExtractorNode> nodes, EntityGraph<E> entityGraph) {
        EntityGraphExtractorNode head = EntityGraphExtractorGraphPreparer.createHead(nodes);
        EntityGraphExtractorGraphPreparer.fillGraph(entityGraph, head);
    }

    public <E> String getFieldNameWithId(EntityGraphExtractorContext<E> context) {
        List<E> entities = context.getEntities_();
        E entity = entities.get(0);
        Class<?> aClass = entity.getClass();
        return Arrays.stream(aClass.getDeclaredFields())
            .filter(this::isIdAnnotation)
            .map(Field::getName)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("can't find field with @Id"));
    }

    private boolean isIdAnnotation(Field field) {
        return Arrays.stream(field.getDeclaredAnnotations())
            .anyMatch(declaredAnnotation -> declaredAnnotation.annotationType().equals(Id.class));
    }

    public <E> Set<Long> getIds(EntityGraphExtractorContext<E> context) {
        return context.getEntities_().stream()
            .map(context::getId_)
            .collect(Collectors.toSet());
    }

}
