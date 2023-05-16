package com.company.app.telegram.domain.entity;

import lombok.*;

import javax.persistence.*;

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

	@Column(name = "FOOT_SIZE")
	private String footSize;

	@Column(name = "DRESS_SIZE")
	private String dressSize;

	@Column(name = "SUPPLIER")
	private String supplier;

	@Column(name = "GREED_INDEX")
	private String greedIndex;
}
