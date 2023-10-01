package com.company.app.wildberries_desire_lot.controller.executor;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire_lot.domain.dto.DesireDto;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WildberriesDesireControllerExecutor {

    private final DesireRepository desireRepository;

    @PerformanceLogAnnotation
    @Transactional
    public List<DesireDto> get(String chatName) {
        List<Desire> desireList = desireRepository.findAll(Specification.where(chatNameIs(chatName))

                        .and((root, criteriaQuery, criteriaBuilder) -> {
                            return criteriaBuilder.equal(root.get("article"), root.get("desireLot").get("article"));
                        })

                        .and((root, criteriaQuery, criteriaBuilder) -> {
                            return criteriaBuilder.lessThan(root.get("price"), root.get("desireLot").get("price"));
//                    return criteriaBuilder.equal(root.get("desireLot").get("article"), root.get("article"));
                        })
        );

        return desireList.stream()
                .map(DesireDto::of)
                .toList();
    }

    private static Specification<Desire> chatNameIs(String chatName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("chatName"), chatName);
    }

    private static Subquery<Long> createSubQuery(CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Desire> root = subquery.from(Desire.class);

        return subquery.select(root.get("id"))
                .where(criteriaBuilder.and(
                                criteriaBuilder.lessThan(root.get("price"), root.get("desireLot").get("price")),
                                criteriaBuilder.equal(root.get("chatName"), "1")
                        )
                );
    }

//    public Subquery<JpaCustomField> byCode(String code) {
//        Subquery<JpaCustomField> subquery = criteriaQuery.subquery(JpaCustomField.class);
//        Root<JpaCustomField> root = subquery.from(JpaCustomField.class);
//        return subquery.select(root)
//                .where(criteriaBuilder.equal(root.get(JpaCustomField_.code), code));
//    }

//    List<Desire> desireList = desireRepository.findAll(Specification.where(chatNameIs(chatName))
//            .and((root, criteriaQuery, criteriaBuilder) -> {
//                return root.in(root.get("id"), createSubQuery(criteriaQuery, criteriaBuilder));
//            })
//    );

}
