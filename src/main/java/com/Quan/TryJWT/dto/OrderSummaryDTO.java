package com.Quan.TryJWT.dto;

import com.Quan.TryJWT.model.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {
	private OrderDetail orderDetail;
	private int size;
}
