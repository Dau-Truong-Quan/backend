package com.Quan.TryJWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Poster;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long>{

}