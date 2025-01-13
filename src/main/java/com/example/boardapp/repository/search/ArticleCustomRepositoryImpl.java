package com.example.boardapp.repository.search;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.example.boardapp.domain.Article;
import com.example.boardapp.domain.QArticle;
import com.example.boardapp.dto.ArticleSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;


/*
 * JPAQueryFactory :
 * - Spring Data JPA에서 QueryDSL을 더 간단하고 효율적으로 사용할 수 있도록 돕는 클래스입니다.
 */

@Repository
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private QArticle article = QArticle.article;  // Q 클래스

    // Querydsl
    private final JPAQueryFactory jpaQueryFactory;

    
    public  ArticleCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    
    /*
            QArticle: ( Q 클래스 생성 )
            - QueryDSL에 의해서 자동으로 생성되는 Article 엔티티에 대한 메타 모델 클래스로 
            - Todo 엔티티와 매핑된 컬럼에 타입 세이프한 방식으로 접근할 수 있다.
            - todo.no, todo.title ...  
            
            타입 세이프 쿼리 작성:
            - 엔티티 필드에 접근할 때 문자열 대신 타입 세이프한 메타모델(Q 클래스)을 사용합니다.
            - 컴파일 타임에 필드의 유효성을 검증할 수 있습니다.
    */
    @Override
    public Page<Article> search(ArticleSearchCondition condition, Pageable pageable) {
        
        // 데이터 조회        
        List<Article> articles = 
            jpaQueryFactory
                .select(article)
                .from(article)
                .where( writerLike(condition.getWriter()) ,   // null이면 조건은 쿼리에 추가되지 않는다.
                        titleLike(condition.getTitle()) ,
                        contentLike(condition.getContent()))
                .offset(pageable.getPageNumber())  // 페이지 번호
                .limit(pageable.getPageSize())   // 페이지당 데이터 개수     
                .fetch();


        // 총 데이터 개수 조회
        JPAQuery<Article> countQuery = 
            jpaQueryFactory
                .select(article)
                .from(article)
                .where( writerLike(condition.getWriter()) ,   // null이면 조건은 쿼리에 추가되지 않는다.
                        titleLike(condition.getTitle()) ,
                        contentLike(condition.getContent()));
                
                
        /*
            Pageable:          
            - Spring Data JAP에서 페이징 요청을 처리하기 위해서 사용되는 인테페이스
            - page : 요청한 페이지 번호, 페이지 번호는 0부터 시작
            - size : 한 페이지에 보여줄 데이터 개수
            - sort : 정렬 조건
        */

      
        return PageableExecutionUtils.getPage(
                articles, 
                pageable, 
                () -> {
                    return countQuery.fetchCount();
                });

        // return new PageImpl(articles, pageable, total);  // total : 총 데이터 개수        
    
    }

    
    //writerEq 메소드는 QueryDSL을 사용하는 조건절을 동적으로 생성하는 유틸리티 메소드입니다. 
    // 주어진 값을 기준으로 필터 조건을 만들어 반환하며, 입력 값이 null인 경우 조건을 무시(제외) 합니다.

    private BooleanExpression writerLike(String writer) {
        return writer == null ? null : article.writer.like("%" + writer + "%");
    }

    private BooleanExpression titleLike(String title) {
        return title == null ? null : article.title.like("%" + title + "%");
    }

    private BooleanExpression contentLike(String content) {
        return content == null ? null : article.content.like("%" + content + "%");
    }

}