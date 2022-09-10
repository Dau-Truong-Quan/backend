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
import com.Quan.TryJWT.model.Category;
import com.Quan.TryJWT.repository.CategoryRepository;
import com.Quan.TryJWT.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if(!category.isPresent()) {
			return null;
		}
		return category.get();
	}
	
	@Override
	public Category getById(long id) {
		return categoryRepository.getById(id);
	}

	@Override
	public Page<Category> getPage(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return categoryRepository.findAll(pageable);
	}

	@Override
	public int getCount() {
		return (int) categoryRepository.count();
	}

	@Override
	public Boolean existsByName(String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}

	@Override
	public Category updateCategory(Category category) {
		return categoryRepository.save(category);
	}

}