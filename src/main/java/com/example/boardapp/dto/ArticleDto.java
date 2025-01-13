package com.example.boardapp.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter 
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long no;
    private String title;
    private String content;
    private String writer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 날짜 및 시간 형식 지정
    private LocalDateTime regDate;  // LocalDate



}
