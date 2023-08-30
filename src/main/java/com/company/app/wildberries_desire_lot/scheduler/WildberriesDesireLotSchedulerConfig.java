package com.company.app.wildberries_desire_lot.scheduler;

import com.company.app.core.util.Collections;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.wildberries_desire_lot.WildberriesDesireLotFacade;
import com.company.app.wildberries_desire_lot.component.data.WildberriesDesireLotUrlCreator;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
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
    private final WildberriesDesireLotFacade wildberriesFacade;

    @Scheduled(fixedDelayString = "${wildberries.desire.lot.timeout}")
    public void doDesireLotSearch() {
        List<FoundItem> desiredLots = wildberriesFacade.doDesireLotSearch();

        if (log.isDebugEnabled()) {
            log.debug("Определены желаемые лоты вб, в количестве: [{}].", desiredLots.size());
        }

        if (Collections.isNotEmpty(desiredLots)) {
            desiredLots.forEach(foundItem -> telegramFacade.writeToEveryone(WildberriesDesireLotUrlCreator.getUrlForResponse(foundItem.getArticle())));
        }
    }

}
