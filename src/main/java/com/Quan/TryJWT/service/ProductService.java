package com.Quan.TryJWT.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.Quan.TryJWT.model.Product;


public interface ProductService {
	public Page<Product> getAll(int pageNo, int pageSize, String sortField, String sortDirection);
	public List<Product> getAllByCategoryId(long categoryId, int pageNo, int pageSize, String sortField, String sortDirection);
	public List<Product> getAllByCategory(long categoryId);
	public List<Product> getAllByStatus(boolean status, int pageNo, int pageSize, String sortField, String sortDirection);
	public int getCount();
	public int getCountByCategoryId(long categoryId);
	public Product findById(long idProduct);
	public Boolean existsByName(String name);
	public void addProduct(Product product);
	public void deleteProduct(Product product);
	public void updateProduct(Product product);
	public List<Product> getAllProduct();
	
	public Product getProductById(long id);
	public void deleteById(long id);
}
