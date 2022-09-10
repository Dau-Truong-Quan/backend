package com.Quan.TryJWT.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.Exception.NotFoundException;
import com.Quan.TryJWT.model.Brand;
import com.Quan.TryJWT.repository.BrandRepository;
import com.Quan.TryJWT.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	BrandRepository brandRepository;
	
	@Override
	public List<Brand> findAll() {
		return brandRepository.findAll();
	}

	@Override
	public Brand findById(long id) {
		Optional<Brand> brand = brandRepository.findById(id);
		if(!brand.isPresent()) {
			return null;
		}
		return brand.get();
	}

	@Override
	public Page<Brand> getPage(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return brandRepository.findAll(pageable);
	}

	@Override
	public int getCount() {
		return (int) brandRepository.count();
	}

	@Override
	public Boolean existsByName(String name) {
		return brandRepository.existsByName(name);
	}

	@Override
	public void addBrand(Brand brand) {
		brandRepository.save(brand);
	}

	@Override
	public void deleteBrand(Brand brand) {
		brandRepository.delete(brand);
	}

	@Override
	public void updateBrand(Brand brand) {
		brandRepository.save(brand);
	}
}
