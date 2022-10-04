package com.Quan.TryJWT.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.Quan.TryJWT.model.Brand;

public interface BrandService {
	public List<Brand> findAll();
	public Brand findById(long id);
	public Page<Brand> getPage(int pageNo, int pageSize, String sortField, String sortDirection);
	public int getCount();
	public Boolean existsByName(String name);
	public void addBrand(Brand brand);
	public void deleteBrand(Brand brand);
	public void updateBrand(Brand brand);
}
