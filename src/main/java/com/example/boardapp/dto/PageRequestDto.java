package com.example.boardapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRequestDto {

    @Builder.Default
    private int page = 1;   // 페이지 번호

    @Builder.Default
    private int size = 10;  // 페이지당 데이터 개수

}
