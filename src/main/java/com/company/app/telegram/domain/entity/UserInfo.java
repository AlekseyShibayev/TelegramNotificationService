package com.company.app.telegram.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "USER_INFO")
public class UserInfo {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "userInfo")
    private Chat chat;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "GENDER")
    private String gender;

}
