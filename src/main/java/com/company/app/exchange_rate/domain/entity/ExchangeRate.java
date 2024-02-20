package com.company.app.exchange_rate.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import com.company.app.exchange_rate.domain.enums.ExchangeRateType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "EXCHANGE_RATE")
public class ExchangeRate {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExchangeRateType type = ExchangeRateType.ALIEXPRESS;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

}
