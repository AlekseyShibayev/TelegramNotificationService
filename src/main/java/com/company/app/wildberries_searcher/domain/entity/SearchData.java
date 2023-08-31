package com.company.app.wildberries_searcher.domain.entity;

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
