package com.company.app.common.timer.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.domain.enums.StatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


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

    @Column(name = "ENTITY_VIEW", nullable = false)
    private String entityView;

    @Column(name = "ACTION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column(name = "STATUS_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", nullable = false)
    private ZonedDateTime createDate;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE", nullable = false)
    private ZonedDateTime updateDate;

}