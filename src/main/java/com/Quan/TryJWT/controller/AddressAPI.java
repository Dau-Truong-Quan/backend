//package com.Quan.TryJWT.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.Quan.TryJWT.model.District;
//import com.Quan.TryJWT.model.Province;
//import com.Quan.TryJWT.model.User;
//import com.Quan.TryJWT.model.Ward;
//import com.Quan.TryJWT.repository.AddressRepository;
//import com.Quan.TryJWT.repository.DistrictRepository;
//import com.Quan.TryJWT.repository.ProvinceRepository;
//import com.Quan.TryJWT.repository.UserRepository;
//import com.Quan.TryJWT.repository.WardRepository;
//import org.modelmapper.ModelMapper;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/address")
//public class AddressAPI {
//    @Autowired
//    private ProvinceRepository provinceDao;
//    @Autowired
//    private DistrictRepository districtDao;
//    @Autowired
//    private WardRepository wardDao;
//    @Autowired
//    private AddressRepository addressDao;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/province")
//    private ResponseEntity<List<Province>> getAllProvince() {
//        ArrayList<Province> list = (ArrayList<Province>) provinceDao.findAll();
//        return ResponseEntity.ok(list);
//    }
//
//    // Get all district by province_id
//    @GetMapping("/district/{id}")
//    private ResponseEntity<List<District>> getAllDistrictByProvinceId(@PathVariable("id") String provinceId) {
//        return ResponseEntity.ok(districtDao.findAllDistrictByIdProvince(provinceId));
//    }
//
//    //
//    @GetMapping("/village/{id}")
//    private ResponseEntity<List<Ward>> getAllVillageByDistrictId(@PathVariable("id") String districtId) {
//        return ResponseEntity.ok(wardDao.findAllVillageByIdDistrict(districtId));
//    }
//
//    @GetMapping("/province1")
//    @ResponseBody
//    private List<Province> getAllProvince1() {
//        ArrayList<Province> list = (ArrayList<Province>) provinceDao.findAll();
//        return list;
//    }
//
//    @GetMapping("/user")
//    private ResponseEntity<List<User>> getUser() {
//        ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
//        return ResponseEntity.ok(list);
//    }
//
//    @GetMapping("/user1")
//    private ResponseEntity<List<User>> getUser1() {
//        ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
//        return ResponseEntity.ok(list);
//    }
//
//    // @GetMapping("/{id}")
//    // private List<String> getAddressById(@PathVariable("id") long addressId){
//    // Address address=addressDao.findByAddressId(addressId);
//    // List<String> listAddressValues= new ArrayList<>();
//    // listAddressValues.add(String.valueOf(address.getAddressId()));
//    // listAddressValues.add(address.getSpecificAddress());
//    // listAddressValues.add(String.valueOf(address.getWard().getWardId()));
//    // listAddressValues.add(String.valueOf(address.getWard().getDistrict().getDistrictId()));
//    // listAddressValues.add(String.valueOf(address.getWard().getDistrict().getProvince().getProvinceId()));
//    // return listAddressValues;
//    // }
//
//    // @GetMapping("/{id}")
//    // private List<String> getAddressById(@PathVariable("id") long addressId){
//    // Address address=addressDao.findByAddressId(addressId);
//    // List<String> listAddressValues= new ArrayList<>();
//    // listAddressValues.add(String.valueOf(address.getAddressId()));
//    // listAddressValues.add(address.getSpecificAddress());
//    // listAddressValues.add(String.valueOf(address.getWard().getWardId()));
//    // listAddressValues.add(String.valueOf(address.getWard().getDistrict().getDistrictId()));1
//    // listAddressValues.add(String.valueOf(address.getWard().getDistrict().getProvince().getProvinceId()));2
//    // return listAddressValues;3
//    // }
//
//}
