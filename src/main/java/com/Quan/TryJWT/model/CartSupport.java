package com.Quan.TryJWT.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CartSupport {
	
	private final List<Cart> items; 
	
	private BigDecimal total;

	public CartSupport() {
		super();
		this.items = new ArrayList<Cart>();
		this.total = new BigDecimal(0); 
		
	} 
	
	public CartSupport(List<Cart> list) {
		super();
		this.items = list;
		this.total = new BigDecimal(0); 
	} 
	public Cart getItem(Product product) {
		for(Cart item: items) {
			if(item.getProduct().getProductId() == product.getProductId()) {
				return item; 
			}
		}
		return null; 
	}
	
	public int getItemCount() {
		return items.size(); 
	}
	
	public int addItem(Product product, int quantity) {
		Cart item = this.getItem(product); 
		this.total =this.total.add( new BigDecimal(product.getPrice()).multiply(new BigDecimal(quantity)));  
		if(item!=null) {//tồn tại rồi: ->update 
			item.setQuantity(item.getQuantity()+quantity);
			
			return 1; //update
		}//chưa tồn tại: ->insert new 
		else {
			item = new Cart(); 
			item.setProduct(product);
			item.setQuantity(quantity);
			items.add(item); 
			
			
			return 2 ; //insert
		}
		
	}
	
	public void addItemFromDB(Cart itemsSample) {
		
			Cart item = new Cart(); 
			item.setCartId(itemsSample.getCartId());
			item.setProduct(itemsSample.getProduct());
			item.setQuantity(itemsSample.getQuantity());
			items.add(item); 
			
			//update total: 
			this.total =this.total.add( new BigDecimal(item.getProduct().getPrice()).multiply(new BigDecimal(item.getQuantity())));  
	
		
	}
	
	
	public void updateItem(Product product, int quantity) {
		Cart item = this.getItem(product); 
		if(item!=null) {
			item.setQuantity(quantity);
			
			this.total  = BigDecimal.valueOf(0); 
			
			this.callTotalPrice();
		}
		
	}
	public void removeItem(Product product) {
		Cart item = this.getItem(product); 
		if(item!=null) {
			items.remove(item); 
			
			this.total  = BigDecimal.valueOf(0); 
			this.callTotalPrice();
		}
	}
	public void clear() {
		this.items.clear();
		this.total = BigDecimal.valueOf(0); 
	}
	public boolean isEmpty() {
		return items.isEmpty(); 
	}
	public void callTotalPrice() {
		for(Cart i: items) {
			BigDecimal priceOfItem = new BigDecimal(i.getProduct().getPrice()).multiply(new BigDecimal(i.getQuantity())); 
			
			total = total.add(priceOfItem); 
		}
	}

}
