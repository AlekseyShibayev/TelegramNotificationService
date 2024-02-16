package com.company.app.common.timer.scheduler.executors;

import java.util.List;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.TimerType;
import com.company.app.common.timer.service.TimerService;
import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class RollbackChatModeToDefaultTimerExecutorTest extends SpringBootTestApplication {

    @Autowired
    private RollbackChatModeToDefaultTimerExecutor timerSchedulerExecutor;
    @Autowired
    private TimerService timerService;

    @Test
    void test_1() {
        Chat owner = chatRepository.findOwner();

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Mode mode = modeRepository.findByType(ModeType.ADD_DESIRE);
            owner.setMode(mode);
            chatRepository.save(owner);
            timerService.start(owner.getChatName(), TimerType.ROLLBACK_CHAT_MODE_TO_DEFAULT);
        });

        timerSchedulerExecutor.execute();

        Chat extracted = entityGraphExtractor.createChatContext(owner)
            .withMode()
            .extractOne();
        Assertions.assertEquals(extracted.getMode().getType(), ModeType.DEFAULT.name());

        List<Timer> timerList = timerRepository.findAll();
        Assertions.assertEquals(0, timerList.size());
    }

}