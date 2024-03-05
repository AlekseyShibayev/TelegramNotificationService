package com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.enrich_inpl;

import java.util.function.Consumer;

import com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.model.HabrBuilderContext;


@FunctionalInterface
public interface Enrich extends Consumer<HabrBuilderContext> {
}