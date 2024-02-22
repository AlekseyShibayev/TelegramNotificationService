package com.company.app.wildberries.common.price_history.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "PRICE")
public class Price implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "COST", nullable = false)
    private String cost;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @CreationTimestamp
    @Column(name = "CREATE_DATE", nullable = false)
    private ZonedDateTime createDate;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE", nullable = false)
    private ZonedDateTime updateDate;

}