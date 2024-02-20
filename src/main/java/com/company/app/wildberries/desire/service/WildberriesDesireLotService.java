package com.company.app.wildberries.desire.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.entity.DesireLot;
import com.company.app.wildberries.desire.domain.repository.DesireRepository;
import com.company.app.wildberries.desire.service.search.WildberriesDesireLotHttpRepository;
import com.company.app.wildberries.desire.service.search.WildberriesDesireLotUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WildberriesDesireLotService {

    private final DesireRepository desireRepository;
    private final WildberriesDesireLotUpdater wildberriesDesireLotUpdater;
    private final WildberriesDesireLotHttpRepository wildberriesDesireLotHttpRepository;

    @PerformanceLogAnnotation
    @Transactional
    public void search() {
        List<Desire> desireList = desireRepository.findAll();
        List<DesireLot> desireLotList = wildberriesDesireLotHttpRepository.findAllByHttp(desireList);
        List<DesireLot> persistedDesireLots = wildberriesDesireLotUpdater.updateDesireLots(desireLotList);

        Map<String, DesireLot> desireLotPersistedMap = persistedDesireLots.stream()
                .collect(Collectors.toMap(DesireLot::getArticle, Function.identity()));

        for (Desire desire : desireList) {
            DesireLot desireLot = desireLotPersistedMap.get(desire.getArticle());
            desire.setDesireLot(desireLot);
            desireRepository.save(desire);
        }
    }

}
