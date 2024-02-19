package com.company.app.common.outbox.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "OUTBOX")
public class Outbox {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "WHO", nullable = false)
    private String who;

    @Column(name = "WHAT", nullable = false, length = 5000)
    private String what;

    @Column(name = "TARGET", nullable = false)
    @Enumerated(EnumType.STRING)
    private Target target;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", nullable = false)
    private ZonedDateTime createDate;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE", nullable = false)
    private ZonedDateTime updateDate;

}