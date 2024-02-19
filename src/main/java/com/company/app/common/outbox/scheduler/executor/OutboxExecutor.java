package com.company.app.common.outbox.scheduler.executor;

import com.company.app.common.outbox.domain.enums.Target;


public interface OutboxExecutor {

    Target getType();

    void execute();

}
