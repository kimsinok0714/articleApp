package com.example.boardapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.boardapp.domain.Article;
import com.example.boardapp.dto.ArticleDto;
import com.example.boardapp.dto.ArticleSearchCondition;
import com.example.boardapp.dto.PageRequestDto;
import com.example.boardapp.dto.PageResponseDto;
import com.example.boardapp.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;


    @Override
    public Long createrArticle(ArticleDto articleDto) {

        Article article = dtoToEntity(articleDto);
        Article savedArticle = articleRepository.save(article);
        
        return savedArticle.getNo();
    }

    @Override
    public ArticleDto retrieveArticle(Long no) {
        Optional<Article> result = articleRepository.findById(no);
        Article article = result.orElseThrow();

        return entityToDto(article);
        
    }

    @Override
    public PageResponseDto<ArticleDto> retrieveArticleList(PageRequestDto pageRequestDto) {
        
        // 페이지 번호는 0부터 시작한다.
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("no").descending());
        
        Page<Article> page = articleRepository.findAll(pageable);
       
        List<ArticleDto> articleDtoList = page.get().map(article -> entityToDto(article)).collect(Collectors.toList());
        

        return PageResponseDto.<ArticleDto>builder()
                .dtoList(articleDtoList)
                .pageRequestDto(pageRequestDto)
                .total(page.getTotalElements())  // 총 데이터 개수 : 중요
                .build();

    }

    @Override
    public void updateArticle(ArticleDto articleDto) {

        Optional<Article> result = articleRepository.findById(articleDto.getNo());

        Article article = result.orElseThrow();

        article.changeTitle(articleDto.getTitle());
        article.changeContent(articleDto.getContent());
        article.changeWriter(articleDto.getWriter());

        articleRepository.save(article);
        
    }

    @Override
    public PageResponseDto<ArticleDto> searchArticle(ArticleSearchCondition condition, PageRequestDto pageRequestDto) {
        
        // 페이지 번호는 0부터 시작한다.
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("no").descending());

        Page<Article> page = articleRepository.search(condition, pageable);

        List<ArticleDto> articleDtoList = page.get().map(article -> entityToDto(article)).collect(Collectors.toList());
        

        return PageResponseDto.<ArticleDto>builder()
                .dtoList(articleDtoList)
                .pageRequestDto(pageRequestDto)
                .total(page.getTotalElements())  // 총 데이터 개수 : 중요
                .build();

    }


    

}
