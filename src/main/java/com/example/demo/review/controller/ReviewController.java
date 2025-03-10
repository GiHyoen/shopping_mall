package com.example.demo.review.controller;

import com.example.demo.review.domain.Review;
import com.example.demo.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 후기 생성
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        try {
            // 데이터 검증
            if (review.getProductId() == null || review.getContent() == null) {
                return ResponseEntity.badRequest().body("productId와 content는 필수입니다.");
            }
            Review savedReview = reviewService.saveReview(review);
            return ResponseEntity.status(201).body(savedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 특정 상품의 후기 목록 조회
    // 특정 상품의 후기 목록 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        try {
            List<Review> reviews = reviewService.getReviewsByProductId(productId);
            return ResponseEntity.ok(reviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
