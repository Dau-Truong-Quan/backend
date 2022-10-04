package com.Quan.TryJWT.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.Quan.TryJWT.model.Feedback;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.service.FeedbackService;
import com.Quan.TryJWT.service.ProductService;
import com.Quan.TryJWT.service.UserService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FeedbackService feedbackService;
	
	@GetMapping("")
	public ResponseEntity<List<Feedback>> getFeedbacksByProductId(@RequestParam("productId") int productId) 
	{
		List<Feedback> list = feedbackService.getFeedbacksByProductId(productId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Feedback>> getFeedbackByUserIdAndProductId(
			@PathVariable("userId") int userId,
			@RequestParam("productId") int productId) 
	{
		Feedback feedback = feedbackService.getFeedbackByUserIdAndProductId(userId, productId);
		if (feedback == null) {
			return ResponseEntity.ok(new ArrayList<Feedback>());
		}
		return ResponseEntity.ok(Arrays.asList(feedback));
	}
	
	@PostMapping("")
	public ResponseEntity<?> insertFeedback(@RequestBody Feedback feedback) {
		long userId = feedback.getFeedbackId().getUserId();
		User user = userService.findById(userId);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		
		long productId = feedback.getFeedbackId().getIdProduct(); 
		Product product = productService.findById(productId);
		if (product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
		}
		
		feedback.setDate(new Date());
		feedback.setUser(user);
		feedback.setProduct(product);
		feedback = feedbackService.saveFeedback(feedback);

		return AppUtils.returnJS(HttpStatus.OK, "Save feedback successfully!", feedback);
	}
}
