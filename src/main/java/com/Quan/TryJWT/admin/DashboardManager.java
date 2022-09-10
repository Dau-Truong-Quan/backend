package com.Quan.TryJWT.admin;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.dto.DashboadCompare;
import com.Quan.TryJWT.dto.DashboardDTO;
import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.Poster;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.repository.OrderRepository;
import com.Quan.TryJWT.repository.ProductRepository;
import com.Quan.TryJWT.repository.UserRepository;
import com.Quan.TryJWT.service.OrderService;
import com.Quan.TryJWT.service.PosterService;
import com.Quan.TryJWT.service.ProductService;
import com.Quan.TryJWT.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardManager {
	
	@Autowired
	private PosterService posterService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderRepository orderRes;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping
	public ResponseEntity<?> getDashboard() {
		DashboardDTO dashboardDTO = new DashboardDTO();
		dashboardDTO.setTotalProduct(productRepository.findAll().size());
		dashboardDTO.setTotalUser(userRepository.findAll().size());
	
		float  total = 0.0f;
		float  totalToday = 0.0f;
		List<Order> lists = orderRes.getAllOrdeToday();
		for (Order item : lists) {
			totalToday = totalToday + item.getTotalPrice();
		}
		List<Order> lists2 = orderRes.findAll();
		for (Order item : lists2) {
			total = total + item.getTotalPrice();
		}
		dashboardDTO.setTodaySales(totalToday);
		dashboardDTO.setTotalSales(total);
		
		
		return AppUtils.returnJS(HttpStatus.OK, "Get successfully!", dashboardDTO);
	}
	
	@GetMapping("/perYear")
	public ResponseEntity<?> getDashboard(	@RequestParam(name="year")  int year) {
		
		
		List<Float> list = orderService.getMoneyPerMonthByYear(year);
		return AppUtils.returnJS(HttpStatus.OK, "Get successfully!", list);
	}
	@GetMapping("/compare")
	public ResponseEntity<?> getDashboard2() {
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<Float> lastyear = orderService.getMoneyPerMonthByYear(year-1);
		List<Float> thisYear = orderService.getMoneyPerMonthByYear(year);
		DashboadCompare compare = new DashboadCompare();
		compare.setThisYear(thisYear);
		compare.setLastyear(lastyear);
		return AppUtils.returnJS(HttpStatus.OK, "Get successfully!", compare);
	}
	@GetMapping("/getYear")
	public ResponseEntity<?> getYear() {
		
		List<Integer> listYear = orderRepository.getYear();
		return AppUtils.returnJS(HttpStatus.OK, "Get successfully!", listYear);
	}
	
}