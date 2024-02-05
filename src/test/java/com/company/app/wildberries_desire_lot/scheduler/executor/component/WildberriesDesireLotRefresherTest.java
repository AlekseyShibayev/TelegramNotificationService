package com.company.app.wildberries_desire_lot.scheduler.executor.component;

import com.company.app.configuration.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

class WildberriesDesireLotRefresherTest extends SpringBootTestApplicationContext {

    @Autowired
    private WildberriesDesireLotRefresher wildberriesDesireLotRefresher;
    @Autowired
    private DesireRepository desireRepository;

    @Test
    void success_test() {
        Desire desire = new Desire()
                .setChatName("653606407")
                .setArticle("43409221")
                .setPrice(new BigDecimal("699"));
        desireRepository.save(desire);

        wildberriesDesireLotRefresher.refresh();

        Desire extracted = entityGraphExtractor.createDesireContext(desire)
                .withDesireLot()
                .extractOne();
        Assertions.assertEquals(desire.getArticle(), extracted.getDesireLot().getArticle());
        Assertions.assertNotNull(extracted.getDesireLot().getPrice());
    }

}