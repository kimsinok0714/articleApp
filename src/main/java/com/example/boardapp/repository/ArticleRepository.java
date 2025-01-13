package com.example.boardapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.boardapp.domain.Article;
import com.example.boardapp.repository.search.ArticleCustomRepository;



/*
 *  JpaRepository 인터페이스 : 
 *  - Spring Data JPA에서 제공하는 인터페이스
 *  - 데이터베이스와 상호작용하기 위한 기본적인 CRUD 및 페이징, 정렬 기능을 자동으로 제공합니다.
 *  - JpaRepository<T, ID> : 데이터베이스 테이블과 매핑될 엔티티 타입, 엔티티의 Primary Key 타입
 */

@Repository
public interface ArticleRepository extends  JpaRepository<Article, Long>, ArticleCustomRepository {



}
