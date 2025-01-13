package com.example.boardapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.boardapp.dto.ArticleDto;
import com.example.boardapp.dto.ArticleSearchCondition;
import com.example.boardapp.dto.PageRequestDto;
import com.example.boardapp.dto.PageResponseDto;
import com.example.boardapp.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/articles")
    public ResponseEntity<PageResponseDto> getArticleList(PageRequestDto pageRequestDto) {
        
        PageResponseDto<ArticleDto> pageResponseDto = articleService.retrieveArticleList(pageRequestDto);

        return new ResponseEntity<>(pageResponseDto, HttpStatus.OK);
       
    }
    
    @PostMapping("/articles")
    public ResponseEntity<Map<String, Long>> postArticle(@RequestBody ArticleDto articleDto) {

        Long no = articleService.createrArticle(articleDto);
        
        return new ResponseEntity<>(Map.of("no", no), HttpStatus.CREATED);
    }   


    @GetMapping("/articles/{no}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable(value = "no") Long no) {
        
        ArticleDto articleDto = articleService.retrieveArticle(no);
        
        return ResponseEntity.ok().body(articleDto);
    }
    
    
    @PutMapping("/articles/{no}")
    public ResponseEntity<String> putArticle(@PathVariable("no") Long no, @RequestBody ArticleDto articleDto) {

        articleService.retrieveArticle(no);

        articleDto.setNo(no);
        
        articleService.updateArticle(articleDto);
  
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }


    @GetMapping("/search")
    public ResponseEntity<PageResponseDto> searchArticle(@RequestParam("keyfield") String keyfield,
                                                         @RequestParam("keyword") String keyword,
                                                         @ModelAttribute PageRequestDto pageRequestDto) {
              
        ArticleSearchCondition condition  = new ArticleSearchCondition();
        
        if (keyfield.equals("writer")) {
            condition.setWriter(keyword);
        } else if (keyfield.equals("title")) {
            condition.setTitle(keyword);
        } else if (keyfield.equals("content")) {
            condition.setContent(keyword);
        }
    

        PageResponseDto<ArticleDto> pageResponseDto = articleService.searchArticle(condition, pageRequestDto);

        return new ResponseEntity<>(pageResponseDto, HttpStatus.OK);
       
    }
    

}
