package com.example.demo.inquiry.repository;

import com.example.demo.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}