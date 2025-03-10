package com.example.demo.member.controller;

import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberUpdateRequest;
import com.example.demo.member.dto.MemberLoginRequest;
import com.example.demo.member.dto.MemberSignupRequest;
import com.example.demo.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest request) {
        try {
            // 사용자 인증 및 정보 가져오기
            Member member = memberService.authenticate(request.getEmail(), request.getPassword());
            if (member == null) {
                return ResponseEntity.badRequest().body("로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
            }

            // 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("message", "로그인 성공");
            response.put("userId", member.getId()); // 사용자 ID 추가
            response.put("nickname", member.getNickname());
            response.put("email", member.getEmail()); // 이메일 추가 (보안 위험이 없다면)

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 잘못된 요청 처리
            return ResponseEntity.badRequest().body("로그인 실패: " + e.getMessage());
        } catch (Exception e) {
            // 서버 오류 처리
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberSignupRequest request) {
        try {
            memberService.signup(request);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 사용자 정보 조회 API
    @GetMapping("/info/{email}")
    public ResponseEntity<?> getUserInfo(@PathVariable String email) {
        try {
            Map<String, String> userInfo = memberService.getUserInfoByEmail(email);
            return ResponseEntity.ok(userInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("사용자 정보 조회 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }

    // 아이디(이메일) 찾기 API
    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody Map<String, String> request) {
        try {
            String nickname = request.get("nickname");
            String email = memberService.findIdByNickname(nickname);
            return ResponseEntity.ok(Map.of("email", email));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("아이디 찾기 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }

    // 회원정보 수정 API
    @PutMapping("/update")
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest request) {
        try {
            Member updatedMember = memberService.updateMember(request);
            return ResponseEntity.ok(Map.of(
                    "nickname", updatedMember.getNickname(),
                    "email", updatedMember.getEmail()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("회원정보 수정 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }

    // 비밀번호 재설정 API
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            memberService.resetPassword(email);
            return ResponseEntity.ok("비밀번호가 재설정되었습니다. 이메일을 확인하세요.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("비밀번호 재설정 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }
}