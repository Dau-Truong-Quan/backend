package com.Quan.TryJWT.dto;

import java.util.ArrayList;
import java.util.List;

import com.Quan.TryJWT.model.Brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandOutput {

	private int page;
	private int totalPage;
	private List<Brand> listResult = new ArrayList<Brand>();	
	
}
