package com.example.boardapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.boardapp.controller.formatter.LocalDateTimeFormatter;

import lombok.extern.slf4j.Slf4j;



// WebMvcConfigurer : Spring MVC 설정을 커스터마이징할 때 사용하는 인터페이스
@Configuration
@Slf4j
public class CustomServletConfig implements WebMvcConfigurer { 
    @Override
    public void addFormatters(FormatterRegistry registry) {   // FormatterRegistry : 데이터를 특정 형식으로 변환하는데 사용

        log.info("{}", "addFormatter");
        
        registry.addFormatter(new LocalDateTimeFormatter());    // 사용자 정의 포매터 : LocalDateTimeFormatter   
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**") // 모든 경로에 대해 CORS를 허용
                .allowedOrigins( "http://localhost:5173") // 허용할 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(true) // 쿠키 및 인증 정보 허용
                .maxAge(3600); // 캐시 시간 (초 단위)
   
     }

}

