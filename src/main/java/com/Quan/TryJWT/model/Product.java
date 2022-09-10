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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private long productId;

	@NotEmpty(message = "Name must not be empty")
	@Column(name = "name", length = 200)
	private String name;

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "image", length = 300)
	private String image;

	@Min(value = (long) 1.0, message = "Price should be greater than 0")
	@Column(name = "price")
	private float price;

	@Column(name = "specification", length = 50)
	private String specification;

	private int discount;

	@Column(name = "sold_quantity")
	private int soldQuantity;

	@Min(value = 1, message = "Quantity should be greater than 0")
	private int quantity;

	@NotNull(message = "Category must not be null")
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@NotNull(message = "Brand must not be null")
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	private boolean status;

	@Column(name = "calculation_unit ", length = 300)
	private String calculationUnit;

	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Cart> carts = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Feedback> feedBacks = new HashSet<>();

}
