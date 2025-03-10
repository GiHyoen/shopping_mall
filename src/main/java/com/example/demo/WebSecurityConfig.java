package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 허용
                        .requestMatchers("/api/reviews/**").permitAll() // 리뷰 API 인증 없이 허용
                        .requestMatchers("/api/members/login").permitAll()
                        .requestMatchers("/api/members/signup").permitAll()
                        .requestMatchers("/api/inquiries/**").permitAll()
                        .requestMatchers("/api/**").authenticated() // API 인증 필요
                        .anyRequest().permitAll() // 나머지 요청 허용
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // H2 콘솔 사용 시 필요
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // React 프론트엔드 주소
        configuration.addAllowedMethod("*"); // 모든 HTTP 메소드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 인증 정보 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}