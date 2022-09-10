package com.Quan.TryJWT.service;

import java.util.List;

import com.Quan.TryJWT.model.Feedback;
import com.Quan.TryJWT.model.FeedbackId;

public interface FeedbackService {
	public List<Feedback> getFeedbacksByProductId(int productId);
	public Feedback getFeedbackByUserIdAndProductId(long userId, long productId);
	
	public Feedback saveFeedback(Feedback feedback);
	public void deleteFeedback(FeedbackId feedbackId);
}
