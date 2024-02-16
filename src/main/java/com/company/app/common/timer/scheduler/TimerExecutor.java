package com.company.app.common.timer.scheduler;

import com.company.app.common.timer.domain.enums.TimerType;


public interface TimerExecutor {

    TimerType getType();

    void execute();

}
