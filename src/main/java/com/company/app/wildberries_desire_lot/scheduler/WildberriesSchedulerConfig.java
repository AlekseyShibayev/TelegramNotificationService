package com.company.app.wildberries_desire_lot.scheduler;

import com.company.app.telegram.component.TelegramFacade;
import com.company.app.wildberries_desire_lot.component.WildberriesFacade;
import com.company.app.wildberries_desire_lot.component.desire_lot.util.WBUtils;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class WildberriesSchedulerConfig {

    @Autowired
    private TelegramFacade telegramFacade;
    @Autowired
    private WildberriesFacade wildberriesFacade;

    @Scheduled(fixedDelayString = "${wildberries.timeout}")
    public void searchWildberriesLots() {
        List<FoundItem> desiredLots = wildberriesFacade.getDesiredLots();

        if (log.isDebugEnabled()) {
            log.debug("Определены желаемые лоты вб, в количестве: [{}].", desiredLots.size());
        }

        if (CollectionHelper.isNotEmpty(desiredLots)) {
            desiredLots.forEach(foundItem -> telegramFacade.writeToEveryone(WBUtils.getUrlForResponse(foundItem.getArticle())));
        }
    }
}
