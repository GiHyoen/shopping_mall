package com.example.demo.inquiry.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String status; // 상태 (기본값 설정은 생성자에서 처리)

    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일자

    // 기본 생성자
    public Inquiry() {
        this.status = "Pending"; // 기본값
        this.createdAt = LocalDateTime.now(); // 현재 시간으로 초기화
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}