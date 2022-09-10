package com.Quan.TryJWT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
	private String name;
	private String description;
	private String image;
	private float price;
	private String specification;
	private int discount;
	private int soldQuantity;
	private int quantity;
	private long categoryId;
	private long brandId;
}
