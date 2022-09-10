package com.Quan.TryJWT.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "district")
@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "district_id")
	private long districtId;

	@Column(name = "district_name", length = 100)
	private String districtName;

	@Column(name = "district_prefix", length = 100)
	private String districtPrefix;

	@ManyToOne
	@JoinColumn(name = "province_id")
	private Province province;

	@JsonIgnore
	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ward> wards = new HashSet<>();
}
