package com.company.app.common.timer.model;

import com.company.app.common.timer.domain.enums.ActionType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class StartTimerEvent {

    private String entityView;
    private ActionType actionType;

}
