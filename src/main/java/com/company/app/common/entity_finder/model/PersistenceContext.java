package com.company.app.common.entity_finder.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;


@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class PersistenceContext<E> {

    private final Class<E> classGenericType;
    private Specification<E> specification;
    private DynamicEntityGraph dynamicEntityGraph = new DynamicEntityGraph();
    private Integer limit;

    public PersistenceContext<E> with(String... path) {
        dynamicEntityGraph.with(path);
        return this;
    }

}
