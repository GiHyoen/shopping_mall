package com.example.demo.member.service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberSignupRequest;
import com.example.demo.member.dto.MemberLoginRequest;
import com.example.demo.member.dto.MemberUpdateRequest;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 이메일과 비밀번호로 사용자 인증
    public Member authenticate(String email, String password) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return member; // 인증 성공 시 Member 객체 반환
    }

    // 로그인 로직
    public Map<String, Object> login(MemberLoginRequest request) {
        Member member = authenticate(request.getEmail(), request.getPassword()); // 인증 수행

        // 인증 성공 시 사용자 정보 반환
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", member.getId());
        userInfo.put("nickname", member.getNickname());
        return userInfo;
    }

    // 닉네임 조회 로직
    public String getNicknameByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return member.getNickname();
    }

    // 사용자 ID 조회 로직
    public Long getUserIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return member.getId();
    }

    // 회원가입 로직
    public void signup(MemberSignupRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setNickname(request.getNickname());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        member.setTermsAgreed(request.isTermsAgreed());
        memberRepository.save(member);
    }

    // 사용자 정보 조회 로직
    public Map<String, String> getUserInfoByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", member.getEmail());
        userInfo.put("nickname", member.getNickname());
        userInfo.put("phone", member.getPhone());
        userInfo.put("address", member.getAddress());
        return userInfo;
    }

    // 비밀번호 변경 로직
    public void updatePassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    // 아이디(이메일) 찾기 로직
    public String findIdByNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 닉네임으로 아이디를 찾을 수 없습니다."));
        return member.getEmail();
    }

    // 회원정보 수정 로직
    public Member updateMember(MemberUpdateRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 회원을 찾을 수 없습니다."));
        member.setNickname(request.getNickname());
        return memberRepository.save(member); // 수정된 회원 저장
    }

    // 비밀번호 재설정 로직
    public void resetPassword(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 찾을 수 없습니다."));
        String newPassword = "temporaryPassword123"; // 임시 비밀번호
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
        // TODO: 이메일 발송 로직 추가
    }
}