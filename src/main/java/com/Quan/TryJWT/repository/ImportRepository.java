package com.Quan.TryJWT.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Import;
import com.Quan.TryJWT.model.User;



@Repository
public interface ImportRepository extends JpaRepository<Import, Integer>{

	public List<Import> findByUser(User user);
	
	public List<Import> findAllByOrderByDateDesc();

	public long countByDate(Date date);
}
