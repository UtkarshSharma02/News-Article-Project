package com.Articles.service;

import com.Articles.entity.Article;
import com.Articles.payload.ArticleDto;
import com.Articles.payload.ArticleResponse;

import java.util.List;

public interface ArticleService {
    public ArticleDto createArticle(ArticleDto articleDto);
    public ArticleDto retrieveArticleById(Long id);
    public List<ArticleDto> retrieveAllArticles();
    public ArticleDto updateArticle(long id,ArticleDto articleDto);

    public void deleteArticle(long id);
}
