package com.example.demo.inquiry.controller;

import com.example.demo.inquiry.domain.Inquiry;
import com.example.demo.inquiry.service.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // Optional import 추가


@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // 문의 등록
    @PostMapping
    public ResponseEntity<?> createInquiry(@RequestBody Inquiry inquiry) {
        try {
            if (inquiry.getTitle() == null || inquiry.getContent() == null || inquiry.getUserNickname() == null) {
                return ResponseEntity.badRequest().body("문의 제목, 내용 및 사용자 닉네임은 필수입니다.");
            }
            Inquiry savedInquiry = inquiryService.createInquiry(inquiry);
            return ResponseEntity.status(201).body(savedInquiry); // 201 Created 상태 코드와 저장된 문의 반환
        } catch (Exception e) {
            return ResponseEntity.status(500).body("문의 등록 실패: " + e.getMessage()); // 500 Internal Server Error 상태 코드
        }
    }

    // 특정 ID로 문의 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getInquiryById(@PathVariable Long id) {
        try {
            Optional<Inquiry> inquiryOpt = inquiryService.getInquiryById(id);
            if (inquiryOpt.isPresent()) {
                return ResponseEntity.ok(inquiryOpt.get());
            } else {
                return ResponseEntity.status(404).body("해당 ID의 문의를 찾을 수 없습니다."); // 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage()); // 500 Internal Server Error
        }
    }

    // 문의 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInquiry(@PathVariable Long id) {
        try {
            inquiryService.deleteInquiry(id); // 서비스 호출
            return ResponseEntity.ok("문의가 삭제되었습니다."); // 200 OK 상태 코드
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("문의 삭제 실패: " + e.getMessage()); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage()); // 500 Internal Server Error
        }
    }

    // 모든 문의 목록 가져오기
    @GetMapping
    public ResponseEntity<?> getAllInquiries() {
        try {
            List<Inquiry> inquiries = inquiryService.getAllInquiries();
            return ResponseEntity.ok(inquiries); // 200 OK 상태 코드
        } catch (Exception e) {
            return ResponseEntity.status(500).body("문의 목록 가져오기 실패: " + e.getMessage()); // 500 Internal Server Error
        }
    }
}