package com.Quan.TryJWT.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "poster")
@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Poster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name", length = 300)
	private String name;

	private int type;

	@Column(name = "is_active")
	private boolean isActive;
}
