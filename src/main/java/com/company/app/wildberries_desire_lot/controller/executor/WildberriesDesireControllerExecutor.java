package com.company.app.wildberries_desire_lot.controller.executor;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire_lot.controller.dto.FulfilledDesire;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import com.company.app.wildberries_desire_lot.domain.specification.DesireSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WildberriesDesireControllerExecutor {

    private final DesireRepository desireRepository;

    @PerformanceLogAnnotation
    @Transactional
    public List<FulfilledDesire> getFulfilledDesires(String chatName) {
        List<Desire> desireList = desireRepository.findAll(Specification
                .where(DesireSpecification.chatNameIs(chatName))
                .and(DesireSpecification.fulfilledDesire())
        );

        return desireList.stream()
                .map(FulfilledDesire::of)
                .toList();
    }

}
