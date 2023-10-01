package com.company.app.wildberries_desire_lot.scheduler;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries_desire_lot.component.WildberriesDesireLotRefresher;
import com.company.app.core.wildberries_common.WildberriesUrlCreator;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
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
    private final DesireRepository desireRepository;

    @Scheduled(fixedDelayString = "${wildberries.desire.lot.timeout}")
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
