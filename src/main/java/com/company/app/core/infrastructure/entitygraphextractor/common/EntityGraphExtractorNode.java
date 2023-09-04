package com.company.app.core.infrastructure.entitygraphextractor.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EntityGraphExtractorNode {

    private String name;
    private EntityGraphExtractorNode child;
    private List<EntityGraphExtractorNode> nodeList = new ArrayList<>();

}
