package com.company.app.common.data_base_garbage_collector.impl;

import java.util.List;

import com.company.app.common.data_base_garbage_collector.DataBaseGarbageCollector;
import com.company.app.common.timer.domain.enums.StatusType;
import com.company.app.common.timer.domain.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TimerDataBaseGarbageCollector implements DataBaseGarbageCollector {

    private static final List<String> STATUS_TYPES_FOR_REMOVAL = List.of(StatusType.DONE.name(), StatusType.REJECT.name());

    private final TimerRepository timerRepository;

    @Override
    public void clean() {
        timerRepository.deleteAllByStatusTypeIn(STATUS_TYPES_FOR_REMOVAL);
    }

}