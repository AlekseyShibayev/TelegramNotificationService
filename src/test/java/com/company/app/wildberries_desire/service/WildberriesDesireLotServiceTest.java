package com.company.app.wildberries_desire.service;

import java.math.BigDecimal;

import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries_desire.domain.entity.Desire;
import com.company.app.wildberries_desire.domain.entity.Desire_;
import com.company.app.wildberries_desire.domain.repository.DesireRepository;
import com.company.app.wildberries_desire.domain.specification.DesireSpecification;
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

        Desire extractedDesire = entityFinder.findFirst(new PersistenceContext<>(Desire.class)
                .setSpecification(DesireSpecification.chatNameIs(desire.getChatName()))
                .with(Desire_.DESIRE_LOT)).get();

        Assertions.assertEquals(desire.getArticle(), extractedDesire.getDesireLot().getArticle());
        Assertions.assertNotNull(extractedDesire.getDesireLot().getPrice());
    }

}