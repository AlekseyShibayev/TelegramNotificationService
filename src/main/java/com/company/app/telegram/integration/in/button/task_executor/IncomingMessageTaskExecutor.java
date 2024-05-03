package com.company.app.telegram.integration.in.button.task_executor;

import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.integration.in.button.task_executor.impl.AddDesireTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomingMessageTaskExecutor {

    private final AddDesireTaskExecutor addDesireTaskExecutor;

    public void processIncomingMessageTask(String chatName, ModeType modeType) {
        switch (modeType) {
            case ADD_DESIRE -> addDesireTaskExecutor.execute(chatName, modeType.name());
            case UPDATE_SEARCH_DATA -> log.debug("tut");
            default -> throw new UnsupportedOperationException("unsupported mode type: [%s]".formatted(modeType));
        }
    }

}