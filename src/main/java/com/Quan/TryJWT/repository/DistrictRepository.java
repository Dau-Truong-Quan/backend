package com.Quan.TryJWT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.District;
import com.Quan.TryJWT.model.Province;


@Repository
public interface DistrictRepository extends JpaRepository<District, Long>{
	public List<District> findByProvince(Province province); 
	
	@Query(value = "select * \r\n"
			+ "from district \r\n"
			+ "where district.province_id = ?1", nativeQuery = true)
	public List<District> findAllDistrictByIdProvince(String id);
}
