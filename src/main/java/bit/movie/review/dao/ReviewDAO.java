package bit.movie.review.dao;

import java.util.List;

import bit.movie.review.dto.Review;

public interface ReviewDAO {
	public List<Review> selectAllReview();
	
	public List<Review> selectOneReviewById(Review review);
	
	public Review selectOneReview(int code);
	
	public void insertReview(Review review);
	
	public void updateReview(Review review);
}
