package com.Quan.TryJWT.service;

import java.util.List;

import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.ImportDetail;


public interface ImportService {

	public Import findById(Integer id);
	public Import addImport(Import imports);
	public void deleteImport(Import imports);
	public List<ImportDetail> findImportDetailByImportId(Integer importId);
	public List<Import> findAllByOrderByDateDesc();
}
