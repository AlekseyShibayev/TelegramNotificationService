package com.company.app.wildberries_desire_lot.scheduler.executor;

import com.company.app.core.util.Collections;
import com.company.app.core.wildberries_common.WildberriesUrlCreator;
import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries_desire_lot.scheduler.executor.component.WildberriesDesireLotRefresher;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WildberriesDesireLotSchedulerExecutor {

    private final TelegramFacade telegramFacade;
    private final WildberriesDesireLotRefresher wildberriesDesireLotRefresher;
    private final DesireRepository desireRepository;

    public void doDesireLotSearch() {
        wildberriesDesireLotRefresher.refresh();
        List<Desire> desireList = desireRepository.findAllWithDesirePriceGreaterThenRealPrice();
        if (Collections.isNotEmpty(desireList)) {
            desireList.forEach(desire -> {
                String urlForResponse = WildberriesUrlCreator.getUrlForResponse(desire.getArticle());
                telegramFacade.writeToTargetChat(desire.getChatName(), urlForResponse);
            });
        }
    }

}
