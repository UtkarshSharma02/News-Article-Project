package com.Articles.service;

import com.Articles.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long articleId,CommentDto commentDto);
    public List<CommentDto> retrieveCommentsByArticleId(long articleId);
    public CommentDto retrieveCommentByCommentId(long articleId,long commentId);
    public CommentDto updateComment(long articleId,long commentId,CommentDto commentDto);
    public void deleteComment(long articleId,long commentId);
}
