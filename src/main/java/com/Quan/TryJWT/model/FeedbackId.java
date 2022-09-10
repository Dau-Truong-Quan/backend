package com.Quan.TryJWT.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FeedbackId implements Serializable {
	@Column(name = "user_id")
	private long userId;

	@Column(name = "id_product")
	private long idProduct;

	public FeedbackId(long userId, long idProduct) {
		super();
		this.userId = userId;
		this.idProduct = idProduct;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(long idProduct) {
		this.idProduct = idProduct;
	}

	public FeedbackId() {
		super();
	}

}
