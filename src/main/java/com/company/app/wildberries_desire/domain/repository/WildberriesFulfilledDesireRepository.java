package com.company.app.wildberries_desire.domain.repository;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire.domain.dto.FulfilledDesire;
import com.company.app.wildberries_desire.domain.entity.Desire;
import com.company.app.wildberries_desire.domain.specification.DesireSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WildberriesFulfilledDesireRepository {

    private final DesireRepository desireRepository;

    @PerformanceLogAnnotation
    @Transactional(readOnly = true)
    public List<FulfilledDesire> findAllByChatName(String chatName) {
        Specification<Desire> specification = DesireSpecification.chatNameIs(chatName).and(DesireSpecification.fulfilledDesire());
        List<Desire> desireList = desireRepository.findAll(specification);
        return desireList.stream()
                .map(FulfilledDesire::of)
                .toList();
    }

}
