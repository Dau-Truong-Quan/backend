package com.Quan.TryJWT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.District;
import com.Quan.TryJWT.model.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long>{
	public List<Ward> findByDistrict(District district);
	
	@Query(value = "select * \r\n"
			+ "from ward \r\n"
			+ "where ward.district_id = ?1", nativeQuery = true)
	public List<Ward> findAllVillageByIdDistrict(String id);
}