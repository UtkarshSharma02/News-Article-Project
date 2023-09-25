package com.Articles.controller;

import com.Articles.payload.ArticleDto;
import com.Articles.payload.ArticleResponse;
import com.Articles.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "http://localhost:3000")
public class ArticleController {
    private ArticleService  articleService;
    public ArticleController(ArticleService articleService){

        this.articleService = articleService;
    }

    //http://localhost:8080/api/article

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleDto articleDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ArticleDto dto=articleService.createArticle(articleDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);  //201
    }

    //http://localhost:8080/api/article/2
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> retrieveArticleById(@PathVariable("id") long id){
        ArticleDto dto = articleService.retrieveArticleById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK); //200
    }

    //http://localhost:8080/api/article?pageNo=1&pageSize=5&sortBy=type&sortDirect=desc
    @GetMapping
    public ResponseEntity<List<ArticleDto>> retrieveAllArticles(){
        List<ArticleDto> dto=articleService.retrieveAllArticles();
        return new ResponseEntity<>(dto, HttpStatus.OK);  //200
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") long id,@RequestBody ArticleDto articleDto){
        ArticleDto dto = articleService.updateArticle(id,articleDto);
        return new ResponseEntity<>(dto,HttpStatus.OK); //200
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") long id){
        articleService.deleteArticle(id);
        return new ResponseEntity<>("Article has been deleted",HttpStatus.OK); //200
    }
}
