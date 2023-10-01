package com.company.app.wildberries_desire_lot.controller.executor;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire_lot.domain.dto.FulfilledDesire;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WildberriesDesireControllerExecutor {

    private final DesireRepository desireRepository;

    @PerformanceLogAnnotation
    @Transactional
    public List<FulfilledDesire> getFulfilledDesires(String chatName) {
        List<Desire> desireList = desireRepository.findAll(Specification.where(chatNameIs(chatName))
                .and((root, criteriaQuery, criteriaBuilder) -> {
                    root.join("desireLot", JoinType.INNER);
                    return criteriaBuilder.greaterThan(root.get("price"), root.get("desireLot").get("price"));
                })
        );

        return desireList.stream()
                .map(FulfilledDesire::of)
                .toList();
    }

    private static Specification<Desire> chatNameIs(String chatName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("chatName"), chatName);
    }

}
