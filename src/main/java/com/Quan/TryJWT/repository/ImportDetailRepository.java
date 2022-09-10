package com.Quan.TryJWT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.ImportDetail;
import com.Quan.TryJWT.model.Product;


@Repository
public interface ImportDetailRepository extends JpaRepository<ImportDetail, Integer>{

	public List<ImportDetail> findByImports(Import imports);
	
	public ImportDetail findByProduct(Product product);
}
