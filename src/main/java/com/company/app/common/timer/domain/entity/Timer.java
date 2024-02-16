package com.company.app.common.timer.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import com.company.app.common.timer.domain.enums.TimerType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "TIMER")
public class Timer {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CHAT_NAME")
    private String chatName;

    @Column(name = "TIMER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TimerType timerType;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

}
