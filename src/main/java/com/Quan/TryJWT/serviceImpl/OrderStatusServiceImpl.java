package com.Quan.TryJWT.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.model.OrderStatus;
import com.Quan.TryJWT.repository.OrderDetailRepository;
import com.Quan.TryJWT.repository.OrderRepository;
import com.Quan.TryJWT.repository.OrderStatusRepository;
import com.Quan.TryJWT.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService{

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;


	@Override
	public OrderStatus findById(long id) {
		Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(id);
		if(!orderStatusOptional.isPresent()) {
			return null;
		}
		return orderStatusOptional.get();
	}
	
}
