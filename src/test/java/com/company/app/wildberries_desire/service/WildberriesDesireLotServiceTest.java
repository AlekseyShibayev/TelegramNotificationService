package com.company.app.wildberries_desire.service;

import java.math.BigDecimal;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries_desire.domain.entity.Desire;
import com.company.app.wildberries_desire.domain.repository.DesireRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class WildberriesDesireLotServiceTest extends SpringBootTestApplication {

    @Autowired
    private WildberriesDesireLotService wildberriesDesireLotService;
    @Autowired
    private DesireRepository desireRepository;

    @Test
    void search_success_test() {
        Desire desire = new Desire()
            .setChatName("653606407")
            .setArticle("43409221")
            .setPrice(new BigDecimal("699"));
        desireRepository.save(desire);

        wildberriesDesireLotService.search();

        Desire extracted = entityGraphExtractor.createDesireContext(desire)
            .withDesireLot()
            .extractOne();
        Assertions.assertEquals(desire.getArticle(), extracted.getDesireLot().getArticle());
        Assertions.assertNotNull(extracted.getDesireLot().getPrice());
    }

}