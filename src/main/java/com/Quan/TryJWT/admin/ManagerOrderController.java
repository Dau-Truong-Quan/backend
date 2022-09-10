package com.Quan.TryJWT.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.dto.OrderDTO;
import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.OrderStatus;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.repository.OrderDetailRepository;
import com.Quan.TryJWT.service.CartService;
import com.Quan.TryJWT.service.OrderDetailService;
import com.Quan.TryJWT.service.OrderService;
import com.Quan.TryJWT.service.OrderStatusService;
import com.Quan.TryJWT.service.ProductService;
import com.Quan.TryJWT.service.ReportService;
import com.Quan.TryJWT.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/order/")
public class ManagerOrderController {
	
	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;
	
	@Autowired
	OrderStatusService orderStatusService;
	@Autowired
	OrderDetailService orderDetailService;
  
  @Autowired
 	OrderDetailRepository detailRepository;
	
	
	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	ReportService reportService;
	
	@GetMapping("")
	public ResponseEntity<?> getOrdersByUserAndStatus2(@RequestParam("statusId") long statusId) {
		
		if(statusId == 0) {
			
			OrderStatus orderStatus = orderStatusService.findById(statusId);
			List<Order> orders = orderService.findAll();
			List<OrderDTO> orders2 = new ArrayList<OrderDTO>();
			for(Order o : orders) {
				OrderDTO  ode = new OrderDTO();
				ode.setOrderId(o.getOrderId());
				ode.setTotalPrice(o.getTotalPrice());
				ode.setAddress(o.getAddress());
				ode.setDate(o.getDate());
				ode.setUser(o.getUser());
				ode.setStatusId(o.getStatusId());
				ode.setOrderDetails(detailRepository.findByOrder(o));
				
				orders2.add(ode);
			}
			return ResponseEntity.ok(orders2);
		}
		
		
		OrderStatus orderStatus = orderStatusService.findById(statusId);
		if (orderStatus == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Status id not invalid!", null);
		}
		List<Order> orders = orderService.findByStatusIdOrderByDateDesc(orderStatus);
		List<OrderDTO> orders2 = new ArrayList<OrderDTO>();
		for(Order o : orders) {
			OrderDTO  ode = new OrderDTO();
			ode.setOrderId(o.getOrderId());
			ode.setTotalPrice(o.getTotalPrice());
			ode.setAddress(o.getAddress());
			ode.setDate(o.getDate());
			ode.setUser(o.getUser());
			ode.setStatusId(o.getStatusId());
			ode.setOrderDetails(detailRepository.findByOrder(o));
			
			orders2.add(ode);
		}
		System.err.println(statusId);
		return ResponseEntity.ok(orders2);
	}
}
