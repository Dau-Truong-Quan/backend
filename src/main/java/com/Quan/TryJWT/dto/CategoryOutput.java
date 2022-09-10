package com.Quan.TryJWT.dto;

import java.util.ArrayList;
import java.util.List;

import com.Quan.TryJWT.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryOutput {

	private int page;
	private int totalPage;
	private List<Category> listResult = new ArrayList<Category>();	
	
}
