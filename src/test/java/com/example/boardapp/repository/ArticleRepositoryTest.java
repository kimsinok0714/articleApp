package com.example.boardapp.repository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import com.example.boardapp.domain.Article;
import com.example.boardapp.dto.ArticleSearchCondition;
import com.example.boardapp.dto.PageRequestDto;


@DataJpaTest  // @Transactional 지원
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(value = "test")
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    
    @Test
    @Rollback(false)
    public void test() {
        assertNotNull(articleRepository);

        for (int i = 1; i <= 145; i++) {

            Article article = Article.builder()            
                .title("title" + i)
                .content("content" + i)
                .writer("writer" + i)
                .regDate(LocalDateTime.now())
                .build();

            articleRepository.save(article);
        }         

        
    }



    @Test  
    @Rollback(false)
    public void testSave() {

        // given
        Article article = Article.builder()            
            .title("테스트 title")
            .content("테스트 내용")
            .writer("테스트 작성자")
            .regDate(LocalDateTime.now())
            .build();


        // when
        Article savedArticle = articleRepository.save(article);
   

        // then
        assertNotNull(savedArticle.getNo());
        assertEquals(savedArticle.getTitle(), "테스트 title");


    }


    @Test
    public void testFindById() {

        // given
        
        Article article = Article.builder()            
            .title("테스트 title")
            .content("테스트 내용")
            .writer("테스트 작성자")
            .regDate(LocalDateTime.now())
            .build();
        
        Article savedArticle = articleRepository.save(article);


        // when
        Optional<Article> result = articleRepository.findById(savedArticle.getNo());
       

        // then
        assertDoesNotThrow(() -> {

            Article foundArticle = result.orElseThrow();

            System.out.println("foundArticle : " + foundArticle);

        });


    }

    @Test
    public void testUpdate() {

        // given
        Article article = Article.builder()            
            .title("테스트 title")
            .content("테스트 내용")
            .writer("테스트 작성자")
            .regDate(LocalDateTime.now())
            .build();
        
        Article savedArticle = articleRepository.save(article);

        Article foundArticle = articleRepository.findById(savedArticle.getNo()).orElseThrow();

        // when
        foundArticle.changeTitle("title");
        foundArticle.changeContent("content");        
        foundArticle.changeWriter("writer");
        Article result = articleRepository.save(foundArticle);

        // then
        assertEquals(result.getTitle(), "title");
        assertEquals(result.getContent(), "content");
        assertEquals(result.getWriter(), "writer");


    }

    
    @Test
    public void testDelete() {
        //given
        Article article = Article.builder()            
            .title("테스트 title")
            .content("테스트 내용")
            .writer("테스트 작성자")
            .regDate(LocalDateTime.now())
            .build();
        
        Article savedArticle = articleRepository.save(article);

        //when
        articleRepository.deleteById(savedArticle.getNo());

        //then
        assertThrows(NoSuchElementException.class, () -> {

            articleRepository.findById(savedArticle.getNo()).orElseThrow();

        });

        
    }


    @Test
    public void testPaging() {
        
        // given
        /*
            Pageable  :          
            - Spring Data JAP에서 페이징 요청을 처리하기 위해서 사용되는 인테페이스
            - page : 요청한 페이지 번호, 페이지 번호는 0부터 시작
            - size : 한 페이지에 보여줄 데이터 개수
            - sort : 정렬 조건
         */

        for (int i = 1; i <= 100; i++) {

            Article article = Article.builder()            
                .title("title" + i)
                .content("content" + i)
                .writer("writer" + i)
                .regDate(LocalDateTime.now())
                .build();

            articleRepository.save(article);
        }         

        
        Pageable pageable = PageRequest.of(0, 10, Sort.by("no").descending());


        // when
        
        /*
         * Page
         * - 페이징된 결과를 표현하는 객체
         * - 데이터와 페이징 정보를 포함한다.
         * 페이징 정보 : 
         * - 전체 데이터 개수((데이터베이스 내의 총 레코드 수), 전체 페이지 수, 현재 페이지 번호(0 부터 시작), 
         * - 현재 페이지가 첫번째 페이지 인지 확인, 현재 페이지가 마지박 페이지 인지 확인
         */

        Page<Article> page = articleRepository.findAll(pageable);


        // then
        System.out.println("content : " + page.getContent());
        System.out.println("total : " + page.getTotalElements());
        System.out.println("totalPages : " + page.getTotalPages());
        


    }


    @Test
    public void testSearchPaging() {
        
        // given
        // 페이지 번호는 0부터 시작한다.
        Pageable pageable = PageRequest.of(0, 10, Sort.by("no").descending());
       
        ArticleSearchCondition condition = new ArticleSearchCondition();
        condition.setTitle("title1");

        // when
        Page<Article> result = articleRepository.search(condition, pageable);

        // then
        System.out.println(result.getContent());
        System.out.println(result.getTotalElements());
        System.out.println(result.getTotalPages());
        System.out.println(result.getSize());

    }


}




