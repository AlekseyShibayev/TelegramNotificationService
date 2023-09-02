package com.company.app.exchange_rate.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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

    @Column(name = "ALIEXPRESS_EXCHANGE_RATE")
    private String aliexpressExchangeRate;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

}
