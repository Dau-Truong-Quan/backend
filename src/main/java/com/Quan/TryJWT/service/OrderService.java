package com.Quan.TryJWT.service;

import java.util.List;

import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.OrderDetail;
import com.Quan.TryJWT.model.OrderStatus;
import com.Quan.TryJWT.model.User;

public interface OrderService {

	public Order findById(long id);
	public List<Order> findByStatusId(long statusId);
	public List<OrderDetail> findOrderDetailByOrderId(long statusId);
	public Order updateOrder(Order order, long statusId);
	public List<Order> findByUserAndStatusOrderByDateDesc(User user, OrderStatus orderStatus);
	public List<Order> getAllByUser(User user);
	public List<Order> findByStatusIdOrderByDateDesc(OrderStatus orderStatus);
	public List<Order> findAll();
	public List<Float> getMoneyPerMonthByYear(int year);
}
