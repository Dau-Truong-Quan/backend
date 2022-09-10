package com.Quan.TryJWT.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CartRequest {

	private Long productId;
	private Long userId;
	private int quantity;
	
}
