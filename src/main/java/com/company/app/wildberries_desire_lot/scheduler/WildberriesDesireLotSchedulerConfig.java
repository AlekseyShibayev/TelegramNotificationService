package com.company.app.wildberries_desire_lot.scheduler;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries_desire_lot.component.WildberriesDesireLotFinder;
import com.company.app.wildberries_desire_lot.component.WildberriesDesireLotRefresher;
import com.company.app.wildberries_desire_lot.component.WildberriesDesireLotUrlCreator;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wildberries.desire.lot", name = "enable", havingValue = "true")
public class WildberriesDesireLotSchedulerConfig {

    private final TelegramFacade telegramFacade;
    private final WildberriesDesireLotRefresher wildberriesDesireLotRefresher;
    private final WildberriesDesireLotFinder wildberriesDesireLotFinder;

    @Scheduled(fixedDelayString = "${wildberries.desire.lot.timeout}")
    public void doDesireLotSearch() {
        wildberriesDesireLotRefresher.refresh();
        List<Desire> desiredLots = wildberriesDesireLotFinder.find();
        if (Collections.isNotEmpty(desiredLots)) {
            desiredLots.forEach(foundItem -> telegramFacade.writeToEveryone(WildberriesDesireLotUrlCreator.getUrlForResponse(foundItem.getArticle())));
        }
    }

}
