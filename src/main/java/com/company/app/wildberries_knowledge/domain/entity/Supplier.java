package com.company.app.wildberries_knowledge.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUPPLIER")
public class Supplier {

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "SUPPLIER_NAME")
	private String supplierName;

	@Column(name = "SUPPLIER_ID")
	private String supplierId;
}
