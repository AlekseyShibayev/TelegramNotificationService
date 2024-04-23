package com.company.app.wildberries.desire.service;

import java.math.BigDecimal;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.infrastructure.jpa.entityfinder.model.CommonQuery;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.entity.Desire_;
import com.company.app.wildberries.desire.domain.specification.DesireSpecification;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;


class WildberriesDesireLotServiceTest extends SpringBootTestApplication {

    @Autowired
    private WildberriesDesireLotService wildberriesDesireLotService;

    //    @Test todo fix that
    void search_success_test() {
        Chat owner = chatRepository.findOwner();

        Desire desire = new Desire()
            .setChatName(owner.getChatName())
            .setArticle("43409221")
            .setPrice(new BigDecimal("699"));
        desireRepository.save(desire);

        wildberriesDesireLotService.search();

        Desire extractedDesire = entityFinder.findAllAsList(new CommonQuery<>(Desire.class)
            .setSpecification(DesireSpecification.chatNameIs(desire.getChatName()))
            .with(Desire_.DESIRE_LOT)).get(0);

        Assertions.assertEquals(desire.getArticle(), extractedDesire.getDesireLot().getArticle());
        Assertions.assertNotNull(extractedDesire.getDesireLot().getPrice());
    }

}