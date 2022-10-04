package com.Quan.TryJWT.service;

import java.util.List;

import com.Quan.TryJWT.model.Cart;

import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;

public interface CartService {
  
	public Cart findById(long id);
	public List<Cart> findByUserId(long userId);
	public Cart findByUserIdAndProductId(long userId, long productId);
	
	public Cart saveCart(Cart cart);
	public void deleteCart(long id);

	public List<Cart> getCartByUser(User user);
	
	
	public void updateItemsCartIncreaseQuatity(User user,Product product, int quatity);
	public void updateItemsCartChangeQuatity(User user , Product product, int quatity);
	public void addNewItem(User user, Product product, int quatity);
	public void deleteItems(User user, Product product);
	public void deleteCartAfterBuy(User user);
}
