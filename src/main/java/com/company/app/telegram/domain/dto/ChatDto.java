package com.company.app.telegram.domain.dto;

import java.util.List;
import java.util.Set;

import com.company.app.telegram.domain.entity.History;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private String chatName;
    private boolean enableNotifications;
    private List<History> historyList;
    private UserInfo userInfo;
    private Set<Subscription> subscriptions;

}
