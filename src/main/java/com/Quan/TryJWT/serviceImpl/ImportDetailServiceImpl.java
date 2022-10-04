package com.Quan.TryJWT.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.ImportDetail;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.repository.ImportDetailRepository;
import com.Quan.TryJWT.repository.ImportRepository;
import com.Quan.TryJWT.repository.ProductRepository;
import com.Quan.TryJWT.service.ImportDetailService;



@Service
public class ImportDetailServiceImpl implements ImportDetailService {

	@Autowired
	ImportRepository importRepository;
	
	@Autowired
	ImportDetailRepository importDetailRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<ImportDetail> saveListImportDetail(List<ImportDetail> importDetails) {
		// TODO Auto-generated method stub
		return importDetailRepository.saveAll(importDetails);
	}

	@Override
	public ImportDetail findByProduct(Product product) {
		// TODO Auto-generated method stub
		
		return importDetailRepository.findByProduct(product);
	}

	@Override
	public ImportDetail save(ImportDetail importDetail) {
		// TODO Auto-generated method stub
		return importDetailRepository.save(importDetail);
	}

	@Override
	public List<ImportDetail> findByImports(Import imports) {
		// TODO Auto-generated method stub
		return importDetailRepository.findByImports(imports);
	}

	@Override
	public void deleteImportDetai(ImportDetail importDetail) {
		importDetailRepository.delete(importDetail);
		
	}

	@Override
	public ImportDetail findById(Integer id) {
		// TODO Auto-generated method stub
		return importDetailRepository.findById(id).get();
	}
	
}
