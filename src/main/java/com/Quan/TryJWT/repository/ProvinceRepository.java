package com.Quan.TryJWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Province;


@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>{

}
