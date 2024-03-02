package com.company.app.common.timer.scheduler.executor;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.company.app.common.timer.domain.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TimerExecutorsRegistry {

    private final List<TimerExecutor> list;
    private final Map<ActionType, TimerExecutor> map = new EnumMap<>(ActionType.class);

    @PostConstruct
    void init() {
        for (TimerExecutor timerExecutor : list) {
            if (map.containsKey(timerExecutor.getType())) {
                throw new DuplicateKeyException("check for unique type, current type: [%s]".formatted(timerExecutor.getType()));
            } else {
                map.put(timerExecutor.getType(), timerExecutor);
            }
        }
    }

    public TimerExecutor get(ActionType type) {
        TimerExecutor timerExecutor = map.get(type);
        if (timerExecutor != null) {
            return timerExecutor;
        } else {
            throw new UnsupportedOperationException("unsupported type: [%s]".formatted(type));
        }
    }

}