package com.company.app.telegram.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "INCOMING_MESSAGE_TASK")
public class IncomingMessageTask {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CHAT_NAME", nullable = false)
    private String chatName;

    @Column(name = "MODE_TYPE", nullable = false)
    private String modeType;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private OffsetDateTime createTime;

}
