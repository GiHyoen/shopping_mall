package com.example.demo.inquiry.service;

import com.example.demo.inquiry.domain.Inquiry;
import com.example.demo.inquiry.repository.InquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    // 문의 등록
    public Inquiry createInquiry(Inquiry inquiry) {
        if (inquiry.getTitle() == null || inquiry.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수 입력 항목입니다.");
        }
        if (inquiry.getContent() == null || inquiry.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수 입력 항목입니다.");
        }
        if (inquiry.getUserNickname() == null || inquiry.getUserNickname().trim().isEmpty()) {
            throw new IllegalArgumentException("사용자 닉네임은 필수 입력 항목입니다.");
        }
        return inquiryRepository.save(inquiry);
    }

    // 문의 상세 조회
    public Optional<Inquiry> getInquiryById(Long id) {
        return inquiryRepository.findById(id);
    }

    // 문의 삭제
    public void deleteInquiry(Long id) {
        Optional<Inquiry> inquiry = inquiryRepository.findById(id);

        if (inquiry.isPresent()) {
            inquiryRepository.delete(inquiry.get());
            System.out.println("문의가 삭제되었습니다: ID = " + id);
        } else {
            throw new IllegalArgumentException("해당 ID의 문의를 찾을 수 없습니다.");
        }
    }

    // 모든 문의 가져오기
    public List<Inquiry> getAllInquiries() {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        if (inquiries.isEmpty()) {
            System.out.println("문의 목록이 비어 있습니다.");
        }
        return inquiries;
    }
}