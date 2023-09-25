package com.Articles.service;

import com.Articles.entity.Article;
import com.Articles.entity.Comment;
import com.Articles.exception.ResourceNotFoundException;
import com.Articles.payload.CommentDto;
import com.Articles.repository.ArticleRepository;
import com.Articles.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(long articleId, CommentDto commentDto){
        Comment comment = mapToEntity(commentDto);
        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new ResourceNotFoundException("Article not found with articleId :"+articleId)
        );
        comment.setArticle(article);
        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(saveComment);
        return dto;
    }

    @Override
    public List<CommentDto> retrieveCommentsByArticleId(long articleId){
        List<Comment> comments = commentRepository.findCommentsByArticleId(articleId);
        List<CommentDto> commentsDto = comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
        return commentsDto;
    }

    @Override
    public CommentDto retrieveCommentByCommentId(long articleId,long commentId){
        Article article=articleRepository.findById(articleId).orElseThrow(
                ()-> new ResourceNotFoundException("Article not found with articleId "+articleId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found with commentId "+commentId)
        );
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(long articleId,long commentId,CommentDto commentDto){
        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new ResourceNotFoundException("Article not found with articleId: "+articleId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found with commentId "+commentId)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment saveComment=commentRepository.save(comment);
        CommentDto dto = mapToDto(saveComment);
        return dto;
    }

    @Override
    public void deleteComment(long articleId,long commentId){
        Article article=articleRepository.findById(articleId).orElseThrow(
                ()->new ResourceNotFoundException("Article not found with articleId:"+articleId)
        );
        commentRepository.deleteById(commentId);
    }

    public CommentDto mapToDto(Comment saveComment){
        CommentDto commentDto = modelMapper.map(saveComment,CommentDto.class);
        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto,Comment.class);
        return comment;
    }
}
