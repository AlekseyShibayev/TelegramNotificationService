package com.company.app.common.deprecated.entitygraphextractor.common;

import java.util.ArrayList;
import java.util.List;


public abstract class EntityGraphExtractorAbstractContext<E> implements EntityGraphExtractorContext<E> {

    protected List<E> entities;
    protected EntityGraphExtractorFinisher finisher;
    protected final List<EntityGraphExtractorNode> nodes = new ArrayList<>();

    @Override
    public E extractOne() {
        return finisher.extractOne(this);
    }

    @Override
    public List<E> extractAll() {
        return finisher.extractAll(this);
    }

    @Override
    public List<E> getEntities_() {
        return entities;
    }

    @Override
    public List<EntityGraphExtractorNode> getNodes_() {
        return nodes;
    }

    protected void addParameter(String... strings) {
        if (strings.length < 1) {
            throw new IllegalArgumentException("for graph need minimum one parameter");
        }

        List<EntityGraphExtractorNode> nodeList = new ArrayList<>();

        for (String string : strings) {
            EntityGraphExtractorNode node = new EntityGraphExtractorNode();
            node.setName(string);
            nodeList.add(node);
        }

        for (int i = 1; i < nodeList.size(); i++) {
            EntityGraphExtractorNode childNode = nodeList.get(i);
            EntityGraphExtractorNode parentNode = nodeList.get(i - 1);
            parentNode.setChild(childNode);
        }

        nodes.add(nodeList.get(0));
    }

}
