package com.company.app.telegram.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
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
