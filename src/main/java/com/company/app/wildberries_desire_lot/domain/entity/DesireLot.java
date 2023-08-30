package com.company.app.wildberries_desire_lot.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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
