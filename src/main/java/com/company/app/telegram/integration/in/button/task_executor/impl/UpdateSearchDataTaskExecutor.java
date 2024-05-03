package com.company.app.telegram.integration.in.button.task_executor.impl;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.IncomingMessageTask;
import com.company.app.telegram.domain.repository.IncomingMessageTaskRepository;
import com.company.app.telegram.domain.spec.IncomingMessageTaskSpecification;
import com.company.app.wildberries.search.domain.dto.SearchDataDto;
import com.company.app.wildberries.search.domain.dto.SearchDataUpdate;
import com.company.app.wildberries.search.domain.entity.SearchData;
import com.company.app.wildberries.search.domain.service.SearchDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateSearchDataTaskExecutor {

    private final IncomingMessageTaskRepository incomingMessageTaskRepository;
    private final SearchDataService searchDataService;
    private final TelegramFacade telegramFacade;

    public void execute(String chatName, String modeType) {
        List<IncomingMessageTask> tasks = getIncomingMessageTasks(chatName, modeType);
        String greedIndex = tasks.get(0).getMessage();

        SearchData searchData = searchDataService.update(new SearchDataUpdate()
                .setChatName(chatName)
                .setGreedIndex(greedIndex)
        );

        incomingMessageTaskRepository.deleteAll(tasks);

        telegramFacade.writeToTargetChat(chatName, SearchDataDto.of(searchData));
    }

    private List<IncomingMessageTask> getIncomingMessageTasks(String chatName, String modeType) {
        List<IncomingMessageTask> tasks = incomingMessageTaskRepository.findAll(IncomingMessageTaskSpecification.chatNameIs(chatName)
            .and(IncomingMessageTaskSpecification.modeTypeIs(modeType)));
        tasks.sort(Comparator.comparing(IncomingMessageTask::getCreateTime));
        return tasks;
    }

}