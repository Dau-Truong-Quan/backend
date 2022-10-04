package com.Quan.TryJWT.dto;

import java.util.List;

import com.Quan.TryJWT.model.Order;

public class DashboardDTO {
	int totalProduct;
	int totalUser;
	float todaySales;
	float totalSales;
	
	
	
	public float getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(float totalSales) {
		this.totalSales = totalSales;
	}
	public DashboardDTO(int totalProduct, int totalUser, float todaySales, float totalSales) {
		super();
		this.totalProduct = totalProduct;
		this.totalUser = totalUser;
		this.todaySales = todaySales;
		this.totalSales = totalSales;
	}
	
	public DashboardDTO() {
		super();
	}
	public int getTotalProduct() {
		return totalProduct;
	}
	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public float getTodaySales() {
		return todaySales;
	}
	public void setTodaySales(float todaySales) {
		this.todaySales = todaySales;
	}
	
	
}
