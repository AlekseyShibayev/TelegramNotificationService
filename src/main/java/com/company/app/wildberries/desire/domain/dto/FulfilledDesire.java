package com.company.app.wildberries.desire.domain.dto;

import com.company.app.wildberries.common.WildberriesUrlCreator;
import com.company.app.wildberries.desire.domain.entity.Desire;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FulfilledDesire {

    private String chatName;
    private String article;
    private String url;

    public static FulfilledDesire of(Desire desire) {
        return new FulfilledDesire()
                .setChatName(desire.getChatName())
                .setArticle(desire.getArticle())
                .setUrl(WildberriesUrlCreator.getUrlForResponse(desire.getArticle()));
    }

}
