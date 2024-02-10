package com.company.app.wildberries_desire.domain.specification;

import com.company.app.wildberries_desire.domain.entity.Desire;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;

@UtilityClass
public class DesireSpecification {

    public static Specification<Desire> fulfilledDesire() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            root.join("desireLot", JoinType.INNER);
            return criteriaBuilder.greaterThan(root.get("price"), root.get("desireLot").get("price"));
        };
    }

    public static Specification<Desire> chatNameIs(String chatName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                chatName != null ? criteriaBuilder.equal(root.get("chatName"), chatName) : null;
    }

    public static Specification<Desire> articleIs(String article) {
        return (root, criteriaQuery, criteriaBuilder) ->
                article != null ? criteriaBuilder.equal(root.get("article"), article) : null;
    }

    public static Specification<Desire> priceIs(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) ->
                price != null ? criteriaBuilder.equal(root.get("price"), price) : null;
    }

}
