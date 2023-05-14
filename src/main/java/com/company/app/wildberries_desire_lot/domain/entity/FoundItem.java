package com.company.app.wildberries_desire_lot.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "FOUND_ITEM")
public class FoundItem {

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "ARTICLE")
	private String article;

	@Column(name = "CREATION_DATE")
	private Date creationDate;
}
