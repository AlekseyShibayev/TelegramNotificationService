package com.company.app.common.timer.scheduler;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.company.app.common.timer.domain.enums.TimerType;
import com.company.app.core.exception.DeveloperMistakeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TimerExecutorsRegistry {

    private final List<TimerExecutor> list;
    private final Map<TimerType, TimerExecutor> map = new EnumMap<>(TimerType.class);

    @PostConstruct
    void init() {
        for (TimerExecutor timerExecutor : list) {
            if (map.containsKey(timerExecutor.getType())) {
                throw new DeveloperMistakeException("check for unique type, current type: [%s]".formatted(timerExecutor.getType()));
            } else {
                map.put(timerExecutor.getType(), timerExecutor);
            }
        }
    }

    public TimerExecutor get(TimerType type) {
        TimerExecutor timerExecutor = map.get(type);
        if (timerExecutor != null) {
            return timerExecutor;
        } else {
            throw new DeveloperMistakeException("unsupported type: [%s]".formatted(type));
        }
    }

}