package com.company.app.wildberries_desire_lot.scheduler;

import com.company.app.core.util.Collections;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.wildberries_desire_lot.WildberriesDesireLotFacade;
import com.company.app.wildberries_desire_lot.component.util.WBUtils;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "wildberries.desire.lot", name = "enable", havingValue = "true")
public class WildberriesDesireLotSchedulerConfig {

    @Autowired
    private TelegramFacade telegramFacade;
    @Autowired
    private WildberriesDesireLotFacade wildberriesFacade;

    @Scheduled(fixedDelayString = "${wildberries.desire.lot.timeout}")
    public void searchWildberriesLots() {
        List<FoundItem> desiredLots = wildberriesFacade.getDesiredLots();

        if (log.isDebugEnabled()) {
            log.debug("Определены желаемые лоты вб, в количестве: [{}].", desiredLots.size());
        }

        if (Collections.isNotEmpty(desiredLots)) {
            desiredLots.forEach(foundItem -> telegramFacade.writeToEveryone(WBUtils.getUrlForResponse(foundItem.getArticle())));
        }
    }
}
