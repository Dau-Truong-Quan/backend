package com.Quan.TryJWT.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.Exception.NotFoundException;
import com.Quan.TryJWT.dto.UserDTO;
import com.Quan.TryJWT.model.Address;
import com.Quan.TryJWT.model.ERole;
import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.Role;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.repository.OrderRepository;
import com.Quan.TryJWT.repository.UserRepository;
import com.Quan.TryJWT.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Override
	public User findById(long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return null;
		}
		return user.get();
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Boolean existsByPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}

	@Override
	public void updateProfile(User user) {
		userRepository.save(user);
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		Optional<User> option = userRepository.findByUsername(username);
		if (!option.isPresent()) {
			return null;
		}
		return option.get();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (int) userRepository.count();
	}

	@Override
	public List<User> getAllByStatus(boolean status, int pageNo, int pageSize, String sortField, String sortDirection) {
		// TODO Auto-generated method stub
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		List<User> temp = userRepository.findAllByStatus(status, pageable);
		List<User> users = new ArrayList<User>();
		for (User user : temp) {
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				if (role.getName() == ERole.ROLE_USER) {
					users.add(user);
				}
			}
		}
		return users;
	}

	private boolean checkRoleAdmin(Set<Role> roles) {

		for (Role role : roles) {
			if (role.getName() == ERole.ROLE_ADMIN) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<User> getAllUserByStatus(boolean status) {
		List<User> list = userRepository.findAllByStatus(status).stream().filter(u -> checkRoleAdmin(u.getRoles()))
				.collect(Collectors.toList());
		return userRepository.findAllByStatus(status).stream().filter(u -> checkRoleAdmin(u.getRoles()))
				.collect(Collectors.toList());
	}

	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);

	}

	@Override
	public boolean checkExistEmailInfo(String email, String username) {

		List<User> list = userRepository.verifyDuplicateEmail(email, username);

		if (list.size() > 0)
			return true;
		return false;
	}

	@Override // use for user update info
	public boolean checkExistPhoneInfo(String phone, String username) {
		List<User> list = userRepository.verifyDuplicatePhone(phone, username);

		if (list.size() > 0)
			return true;
		return false;

	}

	@Override
	public int deleteUser(User user) {
		List<Order> listOrder = orderRepository.findByUser(user);
		if (listOrder.size() > 0)
			return 0;
		else {

//			for(Role role : user.getRoles() ) {
//				user.getRoles().remove(role);
//			}
//			userRepository.delete(user);

			user.setStatus(false);
			userRepository.save(user);
			return 1;
		}
	}

	@Override
	public int deleteUser1(Long userID) {
		Optional<User> userOld = userRepository.findById(userID);
		User user = userOld.get();
		user.setStatus(false);
		userRepository.save(user);
		return 1;
	}

	@Override
	public int getNumberOrderById(long idUser) {
		// TODO Auto-generated method stub
		int numOrd = 0;
		try {
			numOrd = userRepository.getNumberOrderById(idUser);
		} catch (Exception ex) {
		}
		return numOrd;
	}

	@Override
	public int getCountByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return (int) userRepository.countByStatus(status);
	}
}
