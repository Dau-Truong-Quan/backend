package com.Quan.TryJWT.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.dto.CategoryOutput;
import com.Quan.TryJWT.model.Category;
import com.Quan.TryJWT.service.CategoryService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> list = categoryService.findAll();
		return ResponseEntity.ok(list);
	}

	@RequestMapping(value = "image/{imageName}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getImage(@PathVariable("imageName") String imageName) throws IOException {

		try {
			ClassPathResource imgFile = new ClassPathResource("images/categories/" + imageName);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(imgFile.getInputStream()));
		} catch (Exception e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Image not found", null);
		}
	}

	@GetMapping
	public ResponseEntity<CategoryOutput> findCategories(
			@RequestParam(value = "pageNo", required = false) Optional<Integer> pPageNo,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pPageSize,
			@RequestParam(value = "sortField", required = false) Optional<String> pSortField,
			@RequestParam(value = "sortDirection", required = false) Optional<String> pSortDir) {
		int pageNo = 1;
		int pageSize = 10;
		String sortField = "categoryId";
		String sortDirection = "ASC";
		if (pPageNo.isPresent()) {
			pageNo = pPageNo.get();
		}
		if (pPageSize.isPresent()) {
			pageSize = pPageSize.get();
		}
		if (pSortField.isPresent()) {
			sortField = pSortField.get();
		}
		if (pSortDir.isPresent()) {
			sortDirection = pSortDir.get();
		}

		int totalPage;
		List<Category> categories = new ArrayList<Category>();

		totalPage = (int) Math.ceil((double) (categoryService.getCount()) / pageSize);
		categories = categoryService.getPage(pageNo, pageSize, sortField, sortDirection).getContent();

		CategoryOutput output = new CategoryOutput();
		output.setPage(pageNo);
		output.setTotalPage(totalPage);
		output.setListResult(categories);
		return ResponseEntity.ok(output);
	}

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getCategoryById(@PathVariable("id") long id) {
		Category category = null;
		
		category = categoryService.findById(id);
		
		if(category == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Category is unavaiable", null);
		
		return ResponseEntity.ok(category);
	}
}
