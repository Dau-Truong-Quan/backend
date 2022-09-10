package com.Quan.TryJWT.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.model.OrderDetail;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.repository.OrderDetailRepository;
import com.Quan.TryJWT.repository.OrderRepository;
import com.Quan.TryJWT.repository.OrderStatusRepository;
import com.Quan.TryJWT.repository.ProductRepository;
import com.Quan.TryJWT.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<OrderDetail> saveListOrderDetail(List<OrderDetail> orderDetails) {
		return orderDetailRepository.saveAll(orderDetails);
	}

	@Override
	public boolean updateSoldQuantityByOrderDetail(List<OrderDetail> orderDetails) {
		for(OrderDetail item : orderDetails)
		{
			Product product = item.getProduct();
			if(product.getSoldQuantity() == product.getQuantity())
				return false;
		}
		
		for(OrderDetail item : orderDetails)
		{
			Product product = item.getProduct();
			product.setSoldQuantity(product.getSoldQuantity() + item.getQuantity());
			productRepository.save(product);
		}
		return true;
	}
	
}
