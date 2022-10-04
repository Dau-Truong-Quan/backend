package com.Quan.TryJWT.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.Quan.TryJWT.model.Category;

public interface CategoryService {
	public List<Category> findAll();
	public Category findById(long id);
	public Category getById(long id);
	public Page<Category> getPage(int pageNo, int pageSize, String sortField, String sortDirection);
	public int getCount();
	public Boolean existsByName(String name);
	public Category addCategory(Category category);
	public void deleteCategory(Category category);
	public Category updateCategory(Category category);
}
