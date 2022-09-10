package com.Quan.TryJWT.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.model.Address;
import com.Quan.TryJWT.model.District;
import com.Quan.TryJWT.model.Province;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.model.Ward;
import com.Quan.TryJWT.repository.DistrictRepository;
import com.Quan.TryJWT.repository.ProvinceRepository;
import com.Quan.TryJWT.repository.UserRepository;
import com.Quan.TryJWT.repository.WardRepository;
import com.Quan.TryJWT.service.AddressService;
import com.Quan.TryJWT.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private ProvinceRepository provinceDao;
    @Autowired
    private DistrictRepository districtDao;
    @Autowired
    private WardRepository wardDao;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/province")
    private ResponseEntity<List<Province>> getAllProvince() {
        ArrayList<Province> list = (ArrayList<Province>) provinceDao.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/district/{id}")
    private ResponseEntity<List<District>> getAllDistrictByProvinceId(@PathVariable("id") String provinceId) {
        return ResponseEntity.ok(districtDao.findAllDistrictByIdProvince(provinceId));
    }

    @GetMapping("/ward/{id}")
    private ResponseEntity<List<Ward>> getAllVillageByDistrictId(@PathVariable("id") String districtId) {
        return ResponseEntity.ok(wardDao.findAllVillageByIdDistrict(districtId));
    }
    
    @GetMapping("/address/{id}")
    private ResponseEntity<List<Address>> getAllAddressByUserId(@PathVariable("id") long userId) {
    	
        return ResponseEntity.ok(addressService.findAllByUserId(userId));
    }
    
    @PostMapping("/address/{userId}")
	public ResponseEntity<?> addAddressToUser(@PathVariable("userId") long id, @Valid @RequestBody Address address, BindingResult bindingResult) {
    	User user = userService.findById(id);
    	if (user == null) {
    		return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		if (bindingResult.hasErrors()) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		}
		address.setUser(user);
		address = addressService.addAddress(address);
		return AppUtils.returnJS(HttpStatus.OK, "Save address successfully!", address);
	}
    
    @PutMapping(value = "/address")
	public ResponseEntity<?> updateAddress(@Valid @RequestBody Address newAddress, BindingResult bindingResult) {
		Address oldAddress = null;
		oldAddress = addressService.findById(newAddress.getAddressId());
		
		if(oldAddress == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Address is unavaiable", null);
		
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);

		newAddress.setUser(oldAddress.getUser());
		addressService.updateAddress1(newAddress);
		return AppUtils.returnJS(HttpStatus.OK, "Update address successfully!", newAddress);
    }
			
    @PutMapping("/address/{id}")
   	public ResponseEntity<?> updateAddressToUser(@PathVariable("id") long id, @Valid @RequestBody Address address) {
    	Address oldAddress = addressService.findById(id);
		if (oldAddress == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Address not found!", null);
		}
		
		oldAddress.setSpecificAddress(address.getSpecificAddress());
		oldAddress.setWard(address.getWard());
		
   		addressService.updateAddress(oldAddress);
   		return AppUtils.returnJS(HttpStatus.OK, "Update address successfully!", oldAddress);
   	}
    
    @DeleteMapping("/address/{id}")
	public ResponseEntity<?> deleteAddressById(@PathVariable("id") long id) {
    	Address address = addressService.findById(id);
		if (address == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Address not found!", null);
		}
		if(address.getUser() != null) {
			User user = userService.findById(address.getUser().getId());
			user.getAddresses().remove(address);
			userRepository.save(user);
		}
		if(address.getWard()!= null) {
			Ward ward = wardDao.findById(address.getWard().getWardId()).get();
			ward.getAddresses().remove(address);
			wardDao.save(ward);
		}
		
		addressService.deleteById(id);
		
		return AppUtils.returnJS(HttpStatus.OK, "Delete address successfully!", null);
	}
}
