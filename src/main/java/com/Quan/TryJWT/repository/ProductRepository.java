package com.Quan.TryJWT.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Category;
import com.Quan.TryJWT.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public Boolean existsByName(String name);
	public List<Product> findAllByCategory(Category category, Pageable pageable);
	public List<Product> findAllByStatus(boolean status, Pageable pageable);
	public int countByCategory(Category category);
	public List<Product> findAllByCategory(Category category);
	public List<Product> findAllByStatus(boolean status);

}