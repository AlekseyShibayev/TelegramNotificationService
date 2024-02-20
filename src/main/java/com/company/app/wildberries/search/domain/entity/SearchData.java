package com.company.app.wildberries.search.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "SEARCH_DATA")
public class SearchData {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CHAT_NAME")
    private String chatName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "FOOT_SIZE")
    private String footSize;

    @Column(name = "DRESS_SIZE")
    private String dressSize;

    @Column(name = "GREED_INDEX")
    private String greedIndex;

}
