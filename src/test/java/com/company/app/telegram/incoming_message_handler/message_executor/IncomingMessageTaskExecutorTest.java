package com.company.app.telegram.incoming_message_handler.message_executor;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.IncomingMessageTask;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.IncomingMessageTaskRepository;
import com.company.app.telegram.incoming_message_handler.button.task_executor.IncomingMessageTaskExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class IncomingMessageTaskExecutorTest extends SpringBootTestApplication {

    @Autowired
    private IncomingMessageTaskExecutor incomingMessageTaskExecutor;
    @Autowired
    private IncomingMessageTaskRepository incomingMessageTaskRepository;

    @Test
    void success_test() {
        Chat owner = chatRepository.findOwner();

        IncomingMessageTask incomingMessageTask1 = new IncomingMessageTask()
                .setChatName(owner.getChatName())
                .setModeType(ModeType.ADD_DESIRE.getType())
                .setMessage("""
                        Шоколад "Темный (70%)" 1кг Для кулинарии Yummy mood
                        https://wildberries.ru/catalog/13341986/detail.aspx
                        """);
        incomingMessageTaskRepository.save(incomingMessageTask1);

        IncomingMessageTask incomingMessageTask2 = new IncomingMessageTask()
                .setChatName(owner.getChatName())
                .setModeType(ModeType.ADD_DESIRE.getType())
                .setMessage("300");
        incomingMessageTaskRepository.save(incomingMessageTask2);

        transactionTemplate.executeWithoutResult(transactionStatus ->
                incomingMessageTaskExecutor.processIncomingMessageTask(owner.getChatName(), ModeType.ADD_DESIRE.getType()));

        Assertions.assertEquals(0, incomingMessageTaskRepository.findAll().size());
    }

}