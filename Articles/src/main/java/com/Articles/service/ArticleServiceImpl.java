package com.Articles.service;

import com.Articles.entity.Article;
import com.Articles.exception.ResourceNotFoundException;
import com.Articles.payload.ArticleDto;
import com.Articles.payload.ArticleResponse;
import com.Articles.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{
    private ArticleRepository articleRepository;
    private ModelMapper modelMapper;
    public ArticleServiceImpl(ArticleRepository articleRepository,ModelMapper modelMapper){
        this.articleRepository=articleRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public ArticleDto createArticle(ArticleDto articleDto){
        Article article = mapToEntity(articleDto);
        Article saveArticle=articleRepository.save(article);
        ArticleDto dto = mapToDto(saveArticle);
        return dto;
    }

    @Override
    public ArticleDto retrieveArticleById(Long id){
        Article article = articleRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Article not found with id:"+id)
        );
        ArticleDto dto = mapToDto(article);
        return dto;
    }

    @Override
    public List<ArticleDto> retrieveAllArticles(){
        List<Article> articles = articleRepository.findAll();
        List<ArticleDto> articlesDto = articles.stream().map(x->mapToDto(x)).collect(Collectors.toList());
        return articlesDto;
    }

    public void deleteArticle(long id){
        articleRepository.deleteById(id);
    }

    public ArticleDto updateArticle(long id,ArticleDto articleDto){
        Article article = articleRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Article not found with id:"+id)
        );
        article.setType(articleDto.getType());
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        Article updatedArticle=articleRepository.save(article);
        ArticleDto dto = mapToDto(updatedArticle);
        return dto;
    }

    public ArticleDto mapToDto(Article saveArticle){
        ArticleDto dto = modelMapper.map(saveArticle,ArticleDto.class);
        /*
        ArticleDto dto = new ArticleDto();
        dto.setId(saveArticle.getId());
        dto.setType(saveArticle.getType());
        dto.setTitle(saveArticle.getTitle());
        dto.setDescription(saveArticle.getDescription());
        */
        return dto;
    }

    public Article mapToEntity(ArticleDto articleDto){
        Article article = modelMapper.map(articleDto,Article.class);
        /*
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setType(articleDto.getType());
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
         */
        return article;
    }
}
