package com.company.app.wildberries_desire_lot.domain.dto;

import com.company.app.wildberries_desire_lot.component.WildberriesUrlCreator;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DesireDto {

    private String chatName;
    private String article;
    private String url;

    public static DesireDto of(Desire desire) {
        return new DesireDto()
                .setChatName(desire.getChatName())
                .setArticle(desire.getArticle())
                .setUrl(WildberriesUrlCreator.getUrlForResponse(desire.getArticle()));
    }

}
