package com.company.app.telegram.domain.model;

import com.company.app.telegram.domain.enums.ModeType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class UpdateChat {

    private Long chatId;
    private ModeType modeType;

}