package com.Quan.TryJWT.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.model.ERole;
import com.Quan.TryJWT.model.Role;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.repository.RoleRepository;
import com.Quan.TryJWT.repository.UserRepository;
import com.Quan.TryJWT.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/user")
public class ManageUserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@ApiOperation(value = "Lấy tất cả danh sách user")
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> userList = userService.getAllUserByStatus(true);
		if (userList == null) {
			userList = new ArrayList<User>();
		}
		return ResponseEntity.ok(userList);
	}

	@PutMapping("/add")
	public ResponseEntity<?> addNew(@Valid @RequestBody User user, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);

		Optional<User> user2 = userRepository.findById(user.getId());

		// Nếu người dùng ko tồn tại (thêm mới)
		if (!user2.isPresent()) {
			Set<Role> roles = new HashSet<>();
			Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
			roles.add(userRole.get());
			if (userRepository.existsByUsername(user.getUsername())) {
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST,
						"User registered failed! " + "Username is already taken!", null);
			}
			if (userRepository.existsByEmail(user.getEmail())) {
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST,
						"User registered failed! " + "Email is already in use!", null);
			}
			if (userRepository.existsByPhone(user.getPhone())) {
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST,
						"User registered failed! " + "Phone is already in use!", null);
			}
			user.setRoles(roles);
			user.setStatus(true);
			String passwordConvert = BCrypt.hashpw("123456", BCrypt.gensalt(12));
			user.setPassword(passwordConvert);

			userService.saveUser(user);
			return AppUtils.returnJS(HttpStatus.OK, "User registered successfully!", null);

		} else {// nếu người dùng đã tồn tại (thực hiện cập nhập)

			// kt trùng
			boolean existPhone = userService.checkExistPhoneInfo(user.getPhone(), user.getUsername());
			boolean existEmail = user.getEmail().isEmpty() ? false
					: userService.checkExistEmailInfo(user.getEmail(), user.getUsername());

			// nếu trùng trả về lỗi
			if (existPhone) {
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST,
						"User registered failed! " + "Phone is already in use!", null);
			}
			if (existEmail) {
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST,
						"User registered failed! " + "Email is already in use!", null);
			}

			userService.saveUser(user);
			return AppUtils.returnJS(HttpStatus.OK, "User registered successfully!", null);
		}

	}

//	@DeleteMapping("/delete")
//	public ResponseEntity<?> updateUser(@RequestParam(name = "id") long id) {
//		User user = userService.findById(id);
//		int status = userService.deleteUser(user);
//		if (status == 0) {
//			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "This user placed an order! ", null);
//		}
//		return AppUtils.returnJS(HttpStatus.OK, "Delete user successfully! ", null);
//	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id") long id) {
		User user = userService.findById(id);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		int status = userService.deleteUser(user);
		if (status == 0) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "This user placed an order!", null);
		}
		return AppUtils.returnJS(HttpStatus.OK, "Delete user successfully!", null);
	}


		


	@PutMapping("/change-password/{password}/{username}")
	public ResponseEntity<?> updatePassword(@RequestParam String password, @RequestParam String username,
			BindingResult bindingResult) {
		User user = userRepository.findByUsername(username).get();

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest()
					.body("Error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}

		if (password.trim().length() < 6) {
			return ResponseEntity.badRequest().body("The length of the password must be least at 6 charaters");
		}
		String passwordConvert = BCrypt.hashpw(password, BCrypt.gensalt(12));
		user.setPassword(passwordConvert);
		userRepository.save(user);
		return ResponseEntity.ok().body("Update password successfully!");
	}
	
//	@PutMapping("/update-new-password")
//  @PreAuthorize("hasRole('MODERATOR')")
//  public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest request){
//      if(request.getPassword().trim().length()<6) return ResponseEntity.badRequest().body("The length of the password must be least at 6 charaters");
//      if (!userRepository.existsByUsername(request.getUsername())) {
//          return ResponseEntity
//                  .badRequest()
//                  .body(new MessageResponse("Error: Username is not exist!"));
//      }
//      User user = userRepository.findByUsername(request.getUsername()).get();
//      user.setPassword(encoder.encode(request.getPassword().trim()));
//      userRepository.save(user);
//      return ResponseEntity.ok().body("Update password successfully!");
//  }
}
