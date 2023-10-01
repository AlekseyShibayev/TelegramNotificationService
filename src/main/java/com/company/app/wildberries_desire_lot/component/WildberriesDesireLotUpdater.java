package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.GetRequestHandler;
import com.company.app.core.temp.data.Response;
import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.core.util.Collections;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
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

    private final DesireRepository desireRepository;
    private final DesireLotRepository desireLotRepository;
    private final GetRequestHandler getRequestHandler;
    private final JsonTool<Response> jsonTool;

    public List<DesireLot> updateDesireLots(List<DesireLot> toUpdate) {
        List<DesireLot> persisted = desireLotRepository.findAll();
        if (isRefreshFirstTime(persisted)) {
            desireLotRepository.saveAll(toUpdate);
        } else {
            Map<String, DesireLot> mapToUpdate = toUpdate.stream()
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
