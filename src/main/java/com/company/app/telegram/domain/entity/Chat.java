package com.company.app.telegram.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "CHAT")
public class Chat {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CHAT_NAME", nullable = false)
    private String chatName;

    @Column(name = "ENABLE_NOTIFICATIONS")
    private boolean enableNotifications;

    @ManyToOne
    @JoinColumn(name = "MODE_ID", nullable = false)
    private Mode mode;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_INFO_ID")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<History> histories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CHATS_SUBSCRIPTIONS")
    private List<Subscription> subscriptions = new ArrayList<>();

}