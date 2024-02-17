package com.company.app.telegram.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "MODE")
public class Mode {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @OneToMany(mappedBy = "mode", fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();

}