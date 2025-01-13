package com.example.boardapp.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.boardapp.dto.ArticleDto;
import com.example.boardapp.dto.PageRequestDto;
import com.example.boardapp.dto.PageResponseDto;



@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;


    @Test
    void testCreaterArticle() { 

        // given
        ArticleDto articleDto = ArticleDto.builder()
            .title("title")
            .content("content")
            .writer("writer")
            .build();

        // when    
        Long no = articleService.createrArticle(articleDto);

        // then
        assertNotNull(no);

    }


    @Test
    void testRetrieveArticle() {

        // given
        ArticleDto articleDto = ArticleDto.builder()
        .title("title")
        .content("content")
        .writer("writer")
        .build();
 
        Long no = articleService.createrArticle(articleDto);

        // when
        ArticleDto foundArticleDto = articleService.retrieveArticle(no);       

        // then
        assertEquals(foundArticleDto.getTitle(), "title");
        assertEquals(foundArticleDto.getContent(), "content");
        assertEquals(foundArticleDto.getWriter(), "writer");

    }



    @Test
    void testRetrieveArticleList() {

       
        // given
        
        // when
        PageRequestDto pageRequestDto = PageRequestDto.builder().build();

        System.out.println("page : " +  pageRequestDto.getPage());
        System.out.println("size : " +  pageRequestDto.getSize());

        PageResponseDto<ArticleDto> pageResponseDto = articleService.retrieveArticleList(pageRequestDto);

        // then
        assertEquals(pageResponseDto.getTotalCount(), 10);



    }

    @Test
    void testUpdateArticle() {

        // given
        ArticleDto articleDto = ArticleDto.builder()
            .no(1L)
            .title("title 수정")
            .content("content 수정")
            .writer("writer 수중")
            .build();


        // when
        articleService.updateArticle(articleDto);

        ArticleDto foundArticleDto = articleService.retrieveArticle(1L);

        // then
        assertEquals(foundArticleDto.getTitle(), articleDto.getTitle());



    }
}
