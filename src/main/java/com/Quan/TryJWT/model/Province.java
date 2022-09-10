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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "province")
@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Province {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
	private long provinceId;
	
	@Column(name="province_name",length = 100)
	private String provinceName;
	
	@Column(name="province_code",length = 20)
	private String provinceCode;
	
	@JsonIgnore
	@OneToMany(mappedBy = "province",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<District> districts = new HashSet<>();
}
