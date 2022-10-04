package com.Quan.TryJWT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Feedback;
import com.Quan.TryJWT.model.FeedbackId;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackId>{
	List<Feedback> findAllByProduct(Product product);
	public Feedback findByUserAndProduct(User user, Product product);
}