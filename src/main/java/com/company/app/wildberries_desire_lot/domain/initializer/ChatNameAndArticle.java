package com.company.app.wildberries_desire_lot.domain.initializer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@AllArgsConstructor
public class ChatNameAndArticle {

    private String chatName;
    private String article;

}
