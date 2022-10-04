package com.Quan.TryJWT.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.ImportDetail;
import com.Quan.TryJWT.repository.ImportDetailRepository;
import com.Quan.TryJWT.repository.ImportRepository;
import com.Quan.TryJWT.service.ImportService;



@Service
public class ImportServiceImpl implements ImportService {

	@Autowired
	ImportRepository importRepository;
	
	@Autowired
	ImportDetailRepository importDetailRepository;

	@Override
	public Import findById(Integer id) {
		Optional<Import> imports = importRepository.findById(id);
		if(!imports.isPresent()) {
			return null;
		}
		return imports.get();
	}

	@Override
	public List<ImportDetail> findImportDetailByImportId(Integer importId) {
		Import imports = importRepository.getById(importId);
		List<ImportDetail> list = importDetailRepository.findByImports(imports);
		return list;
	}

	@Override
	public List<Import> findAllByOrderByDateDesc() {
		return importRepository.findAllByOrderByDateDesc();
	}

	@Override
	public Import addImport(Import imports) {
		return importRepository.save(imports);
	}

	@Override
	public void deleteImport(Import imports) {
		// TODO Auto-generated method stub
		importRepository.delete(imports);
	}
}
