package com.company.app.common.deprecated.entitygraphextractor.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class EntityGraphExtractorNode {

    private String name;
    private EntityGraphExtractorNode child;
    private List<EntityGraphExtractorNode> nodeList = new ArrayList<>();

}
