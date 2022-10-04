package com.Quan.TryJWT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Address;
import com.Quan.TryJWT.model.Cart;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
  
	@Query(value = "SELECT * FROM Cart WHERE user_id = :userId AND quantity > 0", 
			nativeQuery = true)
	public List<Cart> findByUserId(@Param("userId") long userId);
	
	public Cart findByUserAndProduct(User user, Product product);
  
	public List<Cart> findByUser(User user);
	
	public List<Cart> findAllByProduct(Product product);
	
	public void deleteByUser(User user);


	public List<Cart> findAllByUser(User user);
}
