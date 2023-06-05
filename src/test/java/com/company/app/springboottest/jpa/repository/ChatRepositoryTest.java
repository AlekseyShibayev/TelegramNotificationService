package com.company.app.springboottest.jpa.repository;

import com.company.app.springboottest.jpa.DataJpaSpringBootTestContext;
import com.company.app.telegram.domain.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;

@Slf4j
class ChatRepositoryTest extends DataJpaSpringBootTestContext {

    @Autowired
    ChatRepository chatRepository;

    @BeforeEach
    void init() {
//		chatRepository.deleteAll();
    }

    @Disabled
    @Test
    void N_plus_one_test(CapturedOutput capture) {
        log.debug(SELECT_DELIMITER);

        chatRepository.findById(1L);

        Assertions.assertEquals(1, getSelectCount(capture));
    }
}
