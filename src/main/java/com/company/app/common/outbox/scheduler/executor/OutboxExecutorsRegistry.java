package com.company.app.common.outbox.scheduler.executor;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.company.app.common.outbox.domain.enums.Target;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OutboxExecutorsRegistry {

    private final List<OutboxExecutor> list;
    private final Map<Target, OutboxExecutor> map = new EnumMap<>(Target.class);

    @PostConstruct
    void init() {
        for (OutboxExecutor outboxExecutor : list) {
            if (map.containsKey(outboxExecutor.getType())) {
                throw new DuplicateKeyException("check for unique type, current type: [%s]".formatted(outboxExecutor.getType()));
            } else {
                map.put(outboxExecutor.getType(), outboxExecutor);
            }
        }
    }

    public OutboxExecutor get(Target target) {
        OutboxExecutor outboxExecutor = map.get(target);
        if (outboxExecutor != null) {
            return outboxExecutor;
        } else {
            throw new UnsupportedOperationException("unsupported type: [%s]".formatted(target));
        }
    }

}