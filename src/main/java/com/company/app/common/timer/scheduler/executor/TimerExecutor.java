package com.company.app.common.timer.scheduler.executor;

import com.company.app.common.timer.domain.enums.ActionType;


public interface TimerExecutor {

    ActionType getType();

    void execute();

}
