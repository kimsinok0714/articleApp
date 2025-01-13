package com.example.boardapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleSearchCondition {

    private String writer;
    private String title;
    private String content;



}
