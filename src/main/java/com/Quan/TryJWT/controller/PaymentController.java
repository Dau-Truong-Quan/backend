package com.Quan.TryJWT.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.model.Cart;
import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.OrderDetail;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.repository.OrderDetailRepository;
import com.Quan.TryJWT.security.PaypalPaymentIntent;
import com.Quan.TryJWT.security.PaypalPaymentMethod;
import com.Quan.TryJWT.service.CartService;
import com.Quan.TryJWT.service.OrderDetailService;
import com.Quan.TryJWT.service.OrderService;
import com.Quan.TryJWT.service.OrderStatusService;
import com.Quan.TryJWT.service.PaypalService;
import com.Quan.TryJWT.service.ProductService;
import com.Quan.TryJWT.service.ReportService;
import com.Quan.TryJWT.service.UserService;
import com.Quan.TryJWT.utils.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/api/paypal")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {
	public static final String URL_PAYPAL_SUCCESS = "/success";
	public static final String URL_PAYPAL_CANCEL = "/cancel";
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
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PaypalService paypalService;
	@GetMapping("/")
	public String index(){
		return "index";
	}
	@PostMapping("/pay/{id}")
	public String pay(@PathVariable("id") long id, @RequestBody Order order){
		letpayment(id,order);
		String cancelUrl = "http://localhost:3000/payment" 	;
		String successUrl = "http://localhost:3000/account" ;
		try {
			
			Payment payment = paypalService.createPayment(
					order.getTotalPrice()/23,
					"USD",
					PaypalPaymentMethod.paypal,
					PaypalPaymentIntent.sale,
					"payment description",
					cancelUrl,
					successUrl);
		
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					return links.getHref();
				}
			}
			
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "http://localhost:3000";
	}
	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay(){
		return "cancel";
	}
	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,@PathVariable("id") long id, @RequestBody Order order){
		try {
			User user = userService.findById(id);
			
			order.setDate(new Date());
			order.setUser(user);
			order = orderService.updateOrder(order, 1L);
			
			
			List<Cart> listCart = cartService.getCartByUser(user);
			List<OrderDetail> listOrderDetail = new ArrayList<OrderDetail>();
					
					for(Cart s : listCart){
						listOrderDetail.add(new OrderDetail(s.getProduct(),order, s.getQuantity(),s.getProduct().getPrice() ));
						
						user.getCarts().remove(s);
						
						Product product = productService.findById(s.getProduct().getProductId());
						product.setQuantity(product.getQuantity() - s.getQuantity() );
						product.getCarts().remove(s);
						
						userService.saveUser(user);
						productService.addProduct(product);
						
						cartService.deleteCart(s.getCartId());		
					}
			orderDetailService.saveListOrderDetail(listOrderDetail);
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				return "success";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}
	
	void letpayment(@PathVariable("id") long id, @RequestBody Order order) {
		User user = userService.findById(id);
		
		order.setDate(new Date());
		order.setUser(user);
		order = orderService.updateOrder(order, 1L);
		
		
		List<Cart> listCart = cartService.getCartByUser(user);
		List<OrderDetail> listOrderDetail = new ArrayList<OrderDetail>();
				
				for(Cart s : listCart){
					listOrderDetail.add(new OrderDetail(s.getProduct(),order, s.getQuantity(),s.getProduct().getPrice() ));
					
					user.getCarts().remove(s);
					
					Product product = productService.findById(s.getProduct().getProductId());
					product.setQuantity(product.getQuantity() - s.getQuantity() );
					product.getCarts().remove(s);
					
					userService.saveUser(user);
					productService.addProduct(product);
					
					cartService.deleteCart(s.getCartId());		
				}
		orderDetailService.saveListOrderDetail(listOrderDetail);
		
		
		
	}
}
