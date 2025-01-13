package com.example.boardapp.service;



import com.example.boardapp.domain.Article;
import com.example.boardapp.dto.ArticleDto;
import com.example.boardapp.dto.ArticleSearchCondition;
import com.example.boardapp.dto.PageRequestDto;
import com.example.boardapp.dto.PageResponseDto;


public interface ArticleService {

    ArticleDto retrieveArticle(Long no);

    Long createrArticle(ArticleDto articleDto);

    void updateArticle(ArticleDto articleDto);

    PageResponseDto<ArticleDto> retrieveArticleList(PageRequestDto pageRequestDto);

    // 검색 : 동적 쿼리 
    PageResponseDto<ArticleDto> searchArticle(ArticleSearchCondition condition, PageRequestDto pageRequestDto);


    
    default ArticleDto entityToDto(Article article) {

        return ArticleDto.builder()
            .no(article.getNo())
            .title(article.getTitle())
            .content(article.getContent())
            .writer(article.getWriter())
            .regDate(article.getRegDate())
            .build();
        
    }
    

    default Article dtoToEntity(ArticleDto articleDto) {

        return Article.builder()
                .no(articleDto.getNo())
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .writer(articleDto.getWriter())
                .regDate(articleDto.getRegDate())
                .build();

    }
    
}
