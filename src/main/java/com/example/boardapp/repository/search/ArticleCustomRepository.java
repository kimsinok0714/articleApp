package com.example.boardapp.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.boardapp.domain.Article;
import com.example.boardapp.dto.ArticleSearchCondition;
import com.example.boardapp.dto.PageRequestDto;

/*
 * Spring Data JPA 레포지토리에 커스텀 메서드를 추가하려면 인터페이스를 정의해야 합니다. 
 * 
 */

public interface ArticleCustomRepository {

    Page<Article> search(ArticleSearchCondition condition, Pageable pageable);

}
