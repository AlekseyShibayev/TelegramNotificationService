package com.company.app.common.timer.scheduler.executor.impl;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.domain.enums.StatusType;
import com.company.app.common.timer.TimerFacade;
import com.company.app.common.timer.scheduler.executor.impl.RollbackChatModeToDefaultTimerExecutor;
import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class RollbackChatModeToDefaultTimerExecutorTest extends SpringBootTestApplication {

    @Autowired
    private RollbackChatModeToDefaultTimerExecutor rollbackChatModeToDefaultTimerExecutor;
    @Autowired
    private TimerFacade timerFacade;

    @Test
    void test_1() {
        Chat owner = chatRepository.findOwner();

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Mode mode = modeRepository.findByType(ModeType.ADD_DESIRE);
            owner.setMode(mode);
            chatRepository.save(owner);
            timerFacade.start(owner.getChatName(), ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT);
        });

        rollbackChatModeToDefaultTimerExecutor.execute();

        Chat extracted = entityGraphExtractor.createChatContext(owner)
            .withMode()
            .extractOne();
        Assertions.assertEquals(extracted.getMode().getType(), ModeType.DEFAULT.name());

        Timer timer = timerRepository.findAll().get(0);
        Assertions.assertEquals(StatusType.DONE, timer.getStatusType());
        Assertions.assertEquals(ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT, timer.getActionType());
        Assertions.assertEquals(owner.getChatName(), timer.getEntityView());
    }

}