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
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.repository.CategoryRepository;
import com.Quan.TryJWT.repository.ProductRepository;
import com.Quan.TryJWT.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Page<Product> getAll(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return productRepository.findAll(pageable);
	}
	
	@Override
	public List<Product> getAllByCategoryId(long categoryId, int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		Category category = categoryRepository.getById(categoryId);
		return productRepository.findAllByCategory(category, pageable);
	}
	
	@Override
	public List<Product> getAllByStatus(boolean status, int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return productRepository.findAllByStatus(status, pageable);
	}
	
	@Override
	public int getCount() {		
		return (int) productRepository.count();
	}
	
	@Override
	public int getCountByCategoryId(long categoryId) {
		Category category = categoryRepository.getById(categoryId);
		return (int) productRepository.countByCategory(category);
	}

	@Override
	public Product findById(long idProduct) {
		Optional<Product> product = productRepository.findById(idProduct);
		if(!product.isPresent()) {
			return null;
		}
		return product.get();
	}

	@Override
	public Boolean existsByName(String name) {
		return productRepository.existsByName(name);
	}

	@Override
	public void addProduct(Product product) {
		productRepository.save(product);		
	}

	@Override
	public void deleteProduct(Product product) {		

		productRepository.delete(product);		
	}

	@Override
	public void updateProduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return productRepository.findAllByStatus(true);
	}

	@Override
	public Product getProductById(long id) {
		// TODO Auto-generated method stub
		return productRepository.getById(id);
	}

	@Override
	public void deleteById(long id) {
		try {
			boolean isFound = productRepository.existsById(id);
			if(isFound) {
				productRepository.deleteById(id);
			}
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Product> getAllByCategory(long categoryId) {
		Category category = categoryRepository.getById(categoryId);
		return productRepository.findAllByCategory(category);
	}
}
