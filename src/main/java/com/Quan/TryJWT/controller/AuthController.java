package com.Quan.TryJWT.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.Exception.ResponseObject;
import com.Quan.TryJWT.model.ERole;
import com.Quan.TryJWT.model.Role;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.payload.request.LoginRequest;
import com.Quan.TryJWT.payload.request.SignupRequest;
import com.Quan.TryJWT.payload.response.JwtResponse;
import com.Quan.TryJWT.repository.RoleRepository;
import com.Quan.TryJWT.repository.UserRepository;
import com.Quan.TryJWT.security.jwt.JwtUtils;
import com.Quan.TryJWT.security.services.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<ResponseObject> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {		
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User registered failed! " +
					"Username is already taken!", null);
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User registered failed! " +
					"Email is already in use!", null);
		}
		if (userRepository.existsByPhone(signUpRequest.getPhone())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User registered failed! " +
					"Phone is already in use!", null);
		}
		
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
				 signUpRequest.getEmail(),
				 encoder.encode(signUpRequest.getPassword()),signUpRequest.getPhone());;
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		user.setStatus(true);
		if (user.getImage() == null || user.getImage().isEmpty()) {
			user.setImage("userDefaul.png");
		}
		
		try {		
			User userUpdated = userRepository.save(user);
			return AppUtils.returnJS(HttpStatus.OK, "User registered successfully!", userUpdated);
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST,  "User registered failed!" +
					AppUtils.getExceptionSql(e), null);

		}
	}
	
	@GetMapping("/user")
    private ResponseEntity<List<User>> getUser(){
    	ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
        return  ResponseEntity.ok(list);
    }
}