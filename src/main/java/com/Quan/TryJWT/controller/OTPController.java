package com.Quan.TryJWT.controller;

import java.util.Random;

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
import com.Quan.TryJWT.Exception.NotFoundException;
import com.Quan.TryJWT.dto.UserDTO;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.repository.UserRepository;
import com.Quan.TryJWT.security.JavaSenderService;
import com.Quan.TryJWT.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/verify")
public class OTPController {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService userService;
	@Autowired
	JavaSenderService javaSenderService; 
	
	@GetMapping(value = { "/{username}" })
	public ResponseEntity<?> getGmailByUserID(@PathVariable("username") String username) {
		User user = null;
		try {
			user = repository.findByUsername(username).get();
			User userDTO = new User();
			userDTO.setEmail(user.getEmail());
			userDTO.setPhone(user.getPhone());
			
			return ResponseEntity.ok(userDTO);
		} catch (NotFoundException e) {
			return ResponseEntity.badRequest().body("User is unavaiable");
		}
		
	}
	
	@GetMapping(value = { "/phone/{phone}" })
	public ResponseEntity<?> getOTPbyPhone(@PathVariable(name="phone") String phone) {
		
		String code = createCode(); 
		
		javaSenderService.sendSmsVerifyCode(phone, code);
		return ResponseEntity.ok(code);
		
	}
	
	@GetMapping(value = { "/email/{email}/{OTP}" })
	public ResponseEntity<?> getOTPbyMail(@PathVariable(name="email") String email,@PathVariable(name="OTP") String OTP) {
		
		String code = createCode(); 
		
		javaSenderService.sendMailVerifyCode(email, "Mã xác minh của bạn là: "+OTP);
		return ResponseEntity.ok(code);
		
	}
	
	
	String createCode() {
		String code = ""; 
		Random rand = new Random();
		
		for(int i =1;i<=4;i++) {
			int tempIntCode = rand.nextInt(10);
			code +=tempIntCode; 
		}
		
		return code;
	}
}