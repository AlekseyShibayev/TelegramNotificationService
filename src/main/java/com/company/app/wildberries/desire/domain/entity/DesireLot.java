package com.company.app.wildberries.desire.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "DESIRE_LOT")
@Deprecated
public class DesireLot {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ARTICLE")
    private String article;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "UPDATE_TIME")
    @UpdateTimestamp
    private OffsetDateTime updateTime;

    @OneToMany(mappedBy = "desireLot", fetch = FetchType.LAZY)
    private List<Desire> desireList = new ArrayList<>();

}
