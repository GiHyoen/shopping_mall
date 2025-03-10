package com.example.demo.review.service;

import com.example.demo.review.domain.Review;
import com.example.demo.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // 후기 저장
    public Review saveReview(Review review) {
        // 유효성 검사: productId와 content는 필수
        if (review.getProductId() == null || review.getContent() == null) {
            throw new IllegalArgumentException("productId와 content는 필수입니다.");
        }
        return reviewRepository.save(review);
    }

    // 특정 상품의 후기 목록 조회
    public List<Review> getReviewsByProductId(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId는 필수입니다.");
        }
        return reviewRepository.findByProductId(productId);
    }
}