package com.company.app.common.timer.model;

import com.company.app.common.timer.domain.enums.TimerType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class StopTimerEvent {

    private String chatName;
    private TimerType timerType;

}
