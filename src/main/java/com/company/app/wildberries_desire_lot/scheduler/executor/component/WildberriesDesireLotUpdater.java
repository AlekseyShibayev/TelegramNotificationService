package com.company.app.wildberries_desire_lot.scheduler.executor.component;

import com.company.app.core.util.Collections;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WildberriesDesireLotUpdater {

    private final DesireLotRepository desireLotRepository;

    public List<DesireLot> updateDesireLots(List<DesireLot> dtoListToUpdate) {
        List<DesireLot> persisted = desireLotRepository.findAll();
        if (isRefreshFirstTime(persisted)) {
            return desireLotRepository.saveAll(dtoListToUpdate);
        } else {
            Map<String, DesireLot> mapToUpdate = dtoListToUpdate.stream()
                    .collect(Collectors.toMap(DesireLot::getArticle, Function.identity()));

            persisted.forEach(desireLot -> updateOneDesireLot(mapToUpdate, desireLot));
        }
        return persisted;
    }

    private void updateOneDesireLot(Map<String, DesireLot> mapToUpdate, DesireLot desireLot) {
        if (mapToUpdate.containsKey(desireLot.getArticle())) {
            BeanUtils.copyProperties(mapToUpdate.get(desireLot.getArticle()), desireLot, "id");
            desireLotRepository.save(desireLot);
        } else {
            desireLotRepository.save(desireLot);
        }
    }

    private boolean isRefreshFirstTime(List<DesireLot> allDesireLot) {
        return Collections.isEmpty(allDesireLot);
    }

}
