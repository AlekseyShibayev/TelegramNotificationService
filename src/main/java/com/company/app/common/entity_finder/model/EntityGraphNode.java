package com.company.app.common.entity_finder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class EntityGraphNode {

    private String name;
    private EntityGraphNode child;
    private List<EntityGraphNode> nodeList = new ArrayList<>();

}
