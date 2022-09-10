package com.Quan.TryJWT.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Quan.TryJWT.model.OrderDetail;
import com.Quan.TryJWT.model.OrderStatus;
import com.Quan.TryJWT.model.User;

public class OrderDTO {
	private long orderId;
	private Date date;
	private String address;
	private float totalPrice;
	private OrderStatus statusId;
	private User user;
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public OrderStatus getStatusId() {
		return statusId;
	}
	public void setStatusId(OrderStatus statusId) {
		this.statusId = statusId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public OrderDTO() {
		super();
	}
	
	
}
