package com.Quan.TryJWT.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.Exception.NotFoundException;
import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.OrderDetail;
import com.Quan.TryJWT.service.OrderDetailService;
import com.Quan.TryJWT.service.OrderService;
import com.Quan.TryJWT.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	ProductService productService;
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<?> insertOrderDetailByOrderId(@PathVariable("id") long id, @RequestBody List<OrderDetail> orderDetails) {
		Order order = orderService.findById(id);
		if (order == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order not found!", null);
		}
		orderDetailService.saveListOrderDetail(orderDetails);
		return AppUtils.returnJS(HttpStatus.OK, "Save order details successfully!", null);
	}
	
	
}
