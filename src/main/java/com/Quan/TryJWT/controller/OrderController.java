package com.Quan.TryJWT.controller;


import java.security.Principal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.Exception.NotFoundException;
import com.Quan.TryJWT.dto.OrderDTO;
import com.Quan.TryJWT.dto.OrderSummaryDTO;
import com.Quan.TryJWT.model.Cart;

import com.Quan.TryJWT.model.CartSupport;

import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.OrderDetail;
import com.Quan.TryJWT.model.OrderStatus;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;

import com.Quan.TryJWT.payload.response.ResponseBody;
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
@RequestMapping("/api/order")
public class OrderController {

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


	

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getOrderById(@PathVariable("id") long id) {
		Order order = null;
		order = orderService.findById(id);
		if (order == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		return ResponseEntity.ok(order);
	}

	@GetMapping
	public ResponseEntity<?> getOrderByStatus(@RequestParam("statusId") long statusId) {
		List<Order> order = null;
		
		try {
			order = orderService.findByStatusId(statusId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		}
		return ResponseEntity.ok(order);
	}

	@GetMapping(value = "/order-detail")
	public ResponseEntity<?> getOrderDetailById(@RequestParam("orderId") long orderId) {
		List<OrderDetail> list = null;
		try {
			list = orderService.findOrderDetailByOrderId(orderId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		}
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<?> getReport() {
		return ResponseEntity.ok(reportService.reportReceipt(new Date(), 7));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> changeOrderStatus(@PathVariable("id") long id, @RequestParam("statusId") long statusId) {
		Order order = null;

		
		order = orderService.findById(id);

		if (order == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);

		if (statusId == 3)
			if (orderDetailService.updateSoldQuantityByOrderDetail(order.getOrderDetails()) == false)
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order containing product have been sold out", null);

		orderService.updateOrder(order, statusId);

		return AppUtils.returnJS(HttpStatus.OK, "Update order successfully!", null);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getOrdersByUserAndStatus(@PathVariable("id") long id,
			@RequestParam("statusId") long statusId) {
		User user = userService.findById(id);
		if(statusId == 0) {
			List<Order> order = orderService.getAllByUser(user);
			return ResponseEntity.ok(order);
		}
		
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		OrderStatus orderStatus = orderStatusService.findById(statusId);
		if (orderStatus == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Status id not invalid!", null);
		}
		List<Order> orders = orderService.findByUserAndStatusOrderByDateDesc(user, orderStatus);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/user2/{id}")
	public ResponseEntity<?> getOrdersByUserAndStatus2(@PathVariable("id") long id, @RequestParam("statusId") long statusId) {
		User user = userService.findById(id);
		if(statusId == 0) {
			
			OrderStatus orderStatus = orderStatusService.findById(statusId);
			List<Order> orders = orderService.getAllByUser(user);
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
		
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		OrderStatus orderStatus = orderStatusService.findById(statusId);
		if (orderStatus == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Status id not invalid!", null);
		}
		List<Order> orders = orderService.findByUserAndStatusOrderByDateDesc(user, orderStatus);
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

	@GetMapping(value = "/order-summary")
	public ResponseEntity<?> getOrderDetailSummaryByOrderId(@RequestParam("orderId") long orderId) {
		List<OrderDetail> list = null;
		try {
			list = orderService.findOrderDetailByOrderId(orderId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		}
		OrderSummaryDTO dto = new OrderSummaryDTO();
		dto.setOrderDetail(list.get(0));
		dto.setSize(list.size());
		return ResponseEntity.ok(dto);
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<?> insertOrderByUserId(@PathVariable("id") long id, @RequestBody Order order) {
		User user = userService.findById(id);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		order.setDate(new Date());
		order.setUser(user);
		order = orderService.updateOrder(order, 1L);
		return AppUtils.returnJS(HttpStatus.OK, "Save order successfully!", order);
	}
	

	@PostMapping(value = "/add/{id}")
	public ResponseEntity<?> insertOrderByUserId2(@PathVariable("id") long id, @RequestBody Order order) {
		User user = userService.findById(id);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		order.setDate(new Date());
		order.setUser(user);
		order = orderService.updateOrder(order, 1L);
		
		
//		List<Cart> listCart = cartService.getCartByUser(user);
//		List<OrderDetail> listOrderDetail = new ArrayList<OrderDetail>();
//				
//				for(Cart s : listCart){
//					listOrderDetail.add(new OrderDetail(s.getProduct(),order, s.getQuantity(),s.getProduct().getPrice() ));
//					
//					user.getCarts().remove(s);
//					
//					Product product = productService.findById(s.getProduct().getProductId());
//					product.setQuantity(product.getQuantity() - s.getQuantity() );
//					product.getCarts().remove(s);
//					
//					userService.saveUser(user);
//					productService.addProduct(product);
//					
//					cartService.deleteCart(s.getCartId());		
//				}
//		orderDetailService.saveListOrderDetail(listOrderDetail);
		
		
		
		return AppUtils.returnJS(HttpStatus.OK, "Save order successfully!", null);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addToOrder(HttpSession session, @RequestBody Order order,
			Principal principal) {
		
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		order.setDate(new Date());
		order.setUser(user);
		order = orderService.updateOrder(order, 1L);	
		return AppUtils.returnJS(HttpStatus.OK, "Save order successfully!", order);
		
	}
	@PostMapping(value = "/repurchase/{id}")
	public ResponseEntity<?> repurchaseByOrderId(@PathVariable("id") long id) {
		Order order = orderService.findById(id);
		if (order == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order not found!", null);
		}
		User user = userService.findById(order.getUser().getId());
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		List<Cart> addCartList = new ArrayList<Cart>();
		List<OrderDetail> orderDetailList = order.getOrderDetails();
		for (OrderDetail orderDetail : orderDetailList) {
			Product product = productService.findById(orderDetail.getProduct().getProductId());
			if (product == null) {
				// return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
				continue;
			}
			int quantity = orderDetail.getQuantity();
			Cart cart = cartService.findByUserIdAndProductId(user.getId(), product.getProductId());
			if (cart == null) {
				if (quantity > 0 ) {
					cart = new Cart(null, product, user, quantity);
				}
				else {
					// return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Quantity of cart item must be greater than 0!", null);
					continue;
				}
			}
			else {
				int afterQuantity = cart.getQuantity() + quantity;
				afterQuantity = (afterQuantity <= 0) ? 0 : afterQuantity;
				if (afterQuantity > cart.getProduct().getQuantity()) {
					// return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "The remaining quantity of this product is not enough!", null);
					afterQuantity = cart.getProduct().getQuantity();
				}
				cart.setQuantity(afterQuantity);
			}
			cart = cartService.saveCart(cart);
			addCartList.add(cart);
			// return AppUtils.returnJS(HttpStatus.OK, "Add cart successfully!", cart);
		}
		
		return AppUtils.returnJS(HttpStatus.OK, "Add list cart successfully!", addCartList);

	}
}
