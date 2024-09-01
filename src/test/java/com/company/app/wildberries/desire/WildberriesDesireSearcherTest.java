package com.company.app.wildberries.desire;

import com.company.app.common.outbox.domain.entity.Outbox;
import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.core.util.Collections;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries.desire.domain.entity.Desire;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

class WildberriesDesireSearcherTest extends SpringBootTestApplication {

    @Autowired
    private WildberriesDesireSearcher wildberriesDesireSearcher;

    private static final String ARTICLE = "234188662";

    @DisplayName("""
            When real price <= desire price
            then notify
            """)
    @Test
    void success_test() {
        Chat owner = chatRepository.findOwner();

        Desire desire = new Desire()
                .setChatName(owner.getChatName())
                .setArticle(ARTICLE)
                .setPrice(new BigDecimal("5000"));
        desireRepository.save(desire);

        wildberriesDesireSearcher.search();

        List<Outbox> list = outboxRepository.findAll()
                .stream()
                .filter(outbox -> outbox.getWho().equals(owner.getChatName()))
                .filter(outbox -> outbox.getWhat().contains(ARTICLE))
                .toList();
        Assertions.assertTrue(Collections.isNotEmpty(list));
    }

}