package com.Quan.TryJWT.admin;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.Exception.NotFoundException;
import com.Quan.TryJWT.dto.ImportDetailDTO;
import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.ImportDetail;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.service.ImportDetailService;
import com.Quan.TryJWT.service.ImportService;
import com.Quan.TryJWT.service.ProductService;
import com.Quan.TryJWT.service.UserService;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/import")
public class ManageImportController {
	
	@Autowired
	ImportService importService;
	
	@Autowired
	ImportDetailService importDetailService;	
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getAllImport() {
		List<Import> imports = importService.findAllByOrderByDateDesc();
		return ResponseEntity.ok(imports);
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getImportById(@RequestParam Integer id) {
		Import imports = null;
		imports = importService.findById(id);
		if (imports == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Import is unavaiable", null);
		return ResponseEntity.ok(imports);
	}
	
	@PostMapping(value = { "" })
	public ResponseEntity<?> createImport(@RequestParam Integer id) {
		Import imports = new Import();
		imports.setDate(new Date());
		imports.setUser(userService.findById(id));
		if (imports == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Import is unavaiable", null);
		return ResponseEntity.ok(importService.addImport(imports));
	}


	
	
	@GetMapping(value = "/import-detail")
	public ResponseEntity<?> getImportDetailByImportId(@RequestParam("importId") Integer importId) {
		List<ImportDetail> list = null;
		try {
			list = importService.findImportDetailByImportId(importId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Import is unavaiable", null);
		}
		return ResponseEntity.ok(list);
	}
	
	@PostMapping(value = "/addProduct")
	public ResponseEntity<?> insertImportByUserId( @RequestParam("id") int id, @RequestBody ImportDetailDTO importDetailDTO) {
		Product product = productService.findById(importDetailDTO.getProductId());
		Import imports = importService.findById(id);
		ImportDetail importsDetail = importDetailService.findByProduct(product);
		if(importsDetail == null) {

			importsDetail = new ImportDetail();
			importsDetail.setImports(imports);
			importsDetail.setProduct(product);
			importsDetail.setPrice(importDetailDTO.getPrice());
			product.setQuantity(product.getQuantity() + importsDetail.getQuantity() );
			productService.addProduct(product);
			importsDetail.setQuantity(importDetailDTO.getQuantity());
		}else {
			importsDetail.setImports(imports);
			importsDetail.setProduct(product);
			importsDetail.setPrice(importDetailDTO.getPrice());
			product.setQuantity(product.getQuantity() + importsDetail.getQuantity() );
			productService.addProduct(product);
			importsDetail.setQuantity(importDetailDTO.getQuantity()+importsDetail.getQuantity());
		}
		importDetailService.save(importsDetail);
		
		return AppUtils.returnJS(HttpStatus.OK, "Save import successfully!", null);
	}
	
	@PostMapping(value = "/deleteImport")
	public ResponseEntity<?> deleteImportById( @RequestParam("id") int id) {
		Import imports = importService.findById(id);
		List<ImportDetail> listImport = importDetailService.findByImports(imports);
		for (ImportDetail importDetail : listImport) {
			importDetailService.deleteImportDetai(importDetail);
			Product product = productService.findById(importDetail.getProduct().getProductId());
			product.setQuantity(product.getQuantity() - importDetail.getQuantity() );
			productService.addProduct(product);
		}
		importService.deleteImport(imports);
		return AppUtils.returnJS(HttpStatus.OK, "Delete successfully!", null);
	}
	@PostMapping(value = "/deleteImportDetail")
	public ResponseEntity<?> deleteImportDetail( @RequestParam("id") int id) {
	
		ImportDetail importDetail = importDetailService.findById(id);
	
			importDetailService.deleteImportDetai(importDetail);
		
	
		return AppUtils.returnJS(HttpStatus.OK, "Delete successfully!", null);
	}
	
}
