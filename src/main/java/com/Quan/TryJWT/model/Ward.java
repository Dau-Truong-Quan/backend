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
@Table(name = "ward")
@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Ward {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ward_id")
	private long wardId;
	
	@Column(name="ward_name",length = 50)
	private String wardName;
	
	@Column(name="ward_prefix",length = 20)
	private String wardPrefix;
	
	@ManyToOne
    @JoinColumn(name = "district_id")
	private District district;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ward",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

}
