package com.example.demo.member.dto;

public class MemberSignupRequest {

    private String email;
    private String password;
    private String nickname;
    private String phone;
    private String address;
    private boolean termsAgreed;

    // Getters and Setters
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