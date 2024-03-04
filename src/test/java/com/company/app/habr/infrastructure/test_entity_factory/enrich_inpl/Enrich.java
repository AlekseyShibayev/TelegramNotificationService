package com.company.app.habr.infrastructure.test_entity_factory.enrich_inpl;

import java.util.function.Consumer;

import com.company.app.habr.infrastructure.test_entity_factory.model.HabrBuilderContext;


@FunctionalInterface
public interface Enrich extends Consumer<HabrBuilderContext> {
}