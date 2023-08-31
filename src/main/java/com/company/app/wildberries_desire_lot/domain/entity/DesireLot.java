package com.company.app.wildberries_desire_lot.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "DESIRE_LOT")
public class DesireLot {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ARTICLE")
    private String article;

    @Column(name = "DESIRED_PRICE")
    private String desiredPrice;

    @Column(name = "DISCOUNT")
    private String discount;

}
