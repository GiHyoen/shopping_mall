package com.example.demo.member.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 이메일 (고유 식별자)

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String nickname; // 닉네임

    @Column
    private String phone; // 휴대폰 번호

    @Column
    private String address; // 집 주소

    @Column(nullable = false)
    private boolean termsAgreed; // 약관 동의 여부

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public boolean isTermsAgreed() { return termsAgreed; }
    public void setTermsAgreed(boolean termsAgreed) { this.termsAgreed = termsAgreed; }
}