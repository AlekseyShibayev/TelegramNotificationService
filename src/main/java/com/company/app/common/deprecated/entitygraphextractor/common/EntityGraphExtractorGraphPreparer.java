package com.company.app.common.deprecated.entitygraphextractor.common;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import java.util.List;

import lombok.experimental.UtilityClass;


@UtilityClass
public class EntityGraphExtractorGraphPreparer {

    public static EntityGraphExtractorNode createHead(List<EntityGraphExtractorNode> nodes) {
        EntityGraphExtractorNode head = new EntityGraphExtractorNode();
        head.setName("head");

        for (EntityGraphExtractorNode node : nodes) {
            recursionCreate(head, node);
        }

        return head;
    }

    private static void recursionCreate(EntityGraphExtractorNode parent, EntityGraphExtractorNode child) {
        List<EntityGraphExtractorNode> nodeList = parent.getNodeList();
        if (isNotContains(nodeList, child)) {
            nodeList.add(child);
        } else {

            for (EntityGraphExtractorNode node : nodeList) {
                if (node.getName().equals(child.getName())) {
                    child.setNodeList(node.getNodeList());
                }
            }

        }
        if (child.getChild() != null) {
            recursionCreate(child, child.getChild());
        }
    }

    private static boolean isNotContains(List<EntityGraphExtractorNode> parentNodeList, EntityGraphExtractorNode child) {
        for (EntityGraphExtractorNode node : parentNodeList) {
            if (node.getName().equals(child.getName())) {
                return false;
            }
        }
        return true;
    }

    public static <E> void fillGraph(EntityGraph<E> entityGraph, EntityGraphExtractorNode head) {
        List<EntityGraphExtractorNode> headNodeList = head.getNodeList();
        for (EntityGraphExtractorNode node : headNodeList) {
            if (node.getNodeList().isEmpty()) { // если нет детей
                entityGraph.addAttributeNodes(node.getName());
            } else { // дети есть

                Subgraph<E> subgraph = entityGraph.addSubgraph(node.getName()); // создаешь subgraph seconds
                List<EntityGraphExtractorNode> nodeList = node.getNodeList(); // берешь всех детей у seconds
                for (EntityGraphExtractorNode child : nodeList) {
                    if (child.getNodeList().isEmpty()) { // если у seconds ребёнка нет больше детей
                        subgraph.addAttributeNodes(child.getName());
                    } else { // дети есть
                        recursionFill(subgraph, child);
                    }
                }

            }
        }
    }

    private static <E> void recursionFill(Subgraph<E> subgraph, EntityGraphExtractorNode node) {
        List<EntityGraphExtractorNode> nodeList = node.getNodeList();
        if (nodeList.isEmpty()) { // выход из рекурсии
            subgraph.addAttributeNodes(node.getName());
        } else {
            for (EntityGraphExtractorNode child : nodeList) {
                recursionFill(subgraph.addSubgraph(node.getName()), child);
            }
        }
    }

}
