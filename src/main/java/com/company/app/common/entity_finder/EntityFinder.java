package com.company.app.common.entity_finder;

import com.company.app.common.entity_finder.model.DynamicEntityGraph;
import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.core.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * This class have methods for select @Entity
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class EntityFinder {

    private final EntityManager entityManager;

    /**
     * entityGraphExtractor v2 experimental:
     * any @Entity
     * infinity depth
     */
    public <E> List<E> findAll(PersistenceContext<E> persistenceContext) {
        Class<E> entityClass = persistenceContext.getClassGenericType();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> root = criteriaQuery.from(entityClass);

        Specification<E> specification = addNullSafePredicate(persistenceContext.getSpecification());
        criteriaQuery.select(root)
                .where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));

        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);

        DynamicEntityGraph dynamicEntityGraph = persistenceContext.getDynamicEntityGraph();
        if (dynamicEntityGraph.exist()) {
            EntityGraph<E> entityGraph = entityManager.createEntityGraph(entityClass);
            dynamicEntityGraph.prepareGraph(entityGraph);
            typedQuery.setHint(QueryHints.HINT_LOADGRAPH, entityGraph);
        }

        if (persistenceContext.getLimit() != null) {
            typedQuery.setMaxResults(persistenceContext.getLimit());
        }

        return typedQuery.getResultList();
    }

    private <E> Specification<E> addNullSafePredicate(Specification<E> specification) {
        Specification<E> nullSafePredicate = (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
        return nullSafePredicate.and(specification);
    }

    public <E> Optional<E> findFirst(PersistenceContext<E> persistenceContext) {
        List<E> all = findAll(persistenceContext.setLimit(1));
        return Collections.isEmpty(all) ? Optional.empty() : Optional.ofNullable(all.get(0));
    }

}