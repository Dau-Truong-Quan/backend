package com.Quan.TryJWT.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.model.Feedback;
import com.Quan.TryJWT.model.FeedbackId;
import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.repository.FeedbackRepository;
import com.Quan.TryJWT.repository.ProductRepository;
import com.Quan.TryJWT.repository.UserRepository;
import com.Quan.TryJWT.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	FeedbackRepository feedbackRepository;


	@Override
	public List<Feedback> getFeedbacksByProductId(int productId) {
		Product product = productRepository.getById((long)productId);
		return feedbackRepository.findAllByProduct(product);
	}

	@Override
	public Feedback getFeedbackByUserIdAndProductId(long userId, long productId) {
		User user = userRepository.getById(userId);
		Product product = productRepository.getById(productId);
		Feedback feedback = feedbackRepository.findByUserAndProduct(user, product);
		return feedback;
	}

	@Override
	public Feedback saveFeedback(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}

	@Override
	public void deleteFeedback(FeedbackId feedbackId) {
		feedbackRepository.deleteById(feedbackId);
	}

}
