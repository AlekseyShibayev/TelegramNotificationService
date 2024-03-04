package com.company.app.habr.infrastructure.test_entity_factory.model;

import java.util.function.Consumer;


@FunctionalInterface
public interface Enrich extends Consumer<HabrBuilderContext> {
}