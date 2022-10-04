package com.Quan.TryJWT.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.Product;

public class ImportDetailDTO {
	

	private int productId;

	private int quantity;

	private float price;

	public ImportDetailDTO(int productId, int quantity, float price) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	public ImportDetailDTO() {
		super();
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
