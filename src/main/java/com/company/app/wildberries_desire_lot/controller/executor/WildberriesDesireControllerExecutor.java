package com.company.app.wildberries_desire_lot.controller.executor;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire_lot.domain.dto.DesireDto;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
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
    public List<DesireDto> get(String chatName) {
//        List<Desire> desireList = desireRepository.findAll(Specification.where((root, criteriaQuery, criteriaBuilder) -> {
//            return root.in(root.get("id"), createSubQuery(criteriaQuery, criteriaBuilder));
//        }));

        List<Desire> desireList = desireRepository.findAll(Specification.where(chatNameIs(chatName))
                .and((root, query, criteriaBuilder) -> {return null;})
                .and((root, query, criteriaBuilder) -> {return null;})
        );

        return desireList.stream()
                .map(DesireDto::of)
                .toList();
    }

    private static Specification<Desire> chatNameIs(String chatName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("chatName"), chatName);
    }

//    private Subquery<DesireLot> createSubQuery(CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//        Subquery<DesireLot> subquery = criteriaQuery.subquery(DesireLot.class);
//        Root<DesireLot> root = subquery.from(DesireLot.class);
//
//        return subquery.select(root)
//                .where(criteriaBuilder.equal(root.get(""),));
//    }

//    public Subquery<JpaCustomField> byCode(String code) {
//        Subquery<JpaCustomField> subquery = criteriaQuery.subquery(JpaCustomField.class);
//        Root<JpaCustomField> root = subquery.from(JpaCustomField.class);
//        return subquery.select(root)
//                .where(criteriaBuilder.equal(root.get(JpaCustomField_.code), code));
//    }

}
