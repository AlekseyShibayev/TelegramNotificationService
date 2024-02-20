package com.company.app.wildberries.desire.scheduler.executor;

import java.util.List;

import com.company.app.core.util.Collections;
import com.company.app.wildberries.common.WildberriesUrlCreator;
import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.repository.DesireRepository;
import com.company.app.wildberries.desire.service.WildberriesDesireLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WildberriesDesireLotSchedulerExecutor {

    private final TelegramFacade telegramFacade;
    private final WildberriesDesireLotService wildberriesDesireLotService;
    private final DesireRepository desireRepository;

    public void doDesireLotSearch() {
        wildberriesDesireLotService.search();
        List<Desire> desireList = desireRepository.findAllWithDesirePriceGreaterThenRealPrice();
        if (Collections.isNotEmpty(desireList)) {
            desireList.forEach(desire -> {
                String urlForResponse = WildberriesUrlCreator.getUrlForResponse(desire.getArticle());
                telegramFacade.writeToTargetChat(desire.getChatName(), urlForResponse);
            });
        }
    }

}
