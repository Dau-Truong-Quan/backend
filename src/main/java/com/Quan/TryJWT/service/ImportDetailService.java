package com.Quan.TryJWT.service;

import java.util.List;

import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.ImportDetail;
import com.Quan.TryJWT.model.Product;




public interface ImportDetailService {

	public List<ImportDetail> saveListImportDetail(List<ImportDetail> importDetails);
	public ImportDetail findByProduct(Product product);
	public ImportDetail save(ImportDetail importDetail);
	public List<ImportDetail> findByImports(Import imports);
	public void deleteImportDetai(ImportDetail importDetail);
	public ImportDetail findById(Integer id);
}
