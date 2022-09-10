package com.Quan.TryJWT.service;

import java.util.List;

import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.OrderDetail;

public interface OrderDetailService {

	public List<OrderDetail> saveListOrderDetail(List<OrderDetail> orderDetails);
	public boolean updateSoldQuantityByOrderDetail(List<OrderDetail> orderDetails);
	
}
