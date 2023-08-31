package com.company.app.wildberries_searcher.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

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
