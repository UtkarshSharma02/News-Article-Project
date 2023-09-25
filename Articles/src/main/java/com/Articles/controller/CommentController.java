package com.Articles.controller;

import com.Articles.payload.CommentDto;
import com.Articles.repository.CommentRepository;
import com.Articles.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //http://localhost:8080:article/1/comments
    @PostMapping("/article/{articleId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("articleId") long articleId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(articleId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/article/1/comments
    @GetMapping("/article/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> retrieveCommentsByArticleId(@PathVariable("articleId") long articleId){
        List<CommentDto> commentsDto = commentService.retrieveCommentsByArticleId(articleId);
        return new ResponseEntity<>(commentsDto,HttpStatus.OK);
    }

    //http://localhost:8080/api/article/1/comments/2
    @GetMapping("/article/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDto> retrieveCommentByCommentId(
            @PathVariable("articleId") long articleId,
            @PathVariable("commentId") long commentId){
        CommentDto commentDto=commentService.retrieveCommentByCommentId(articleId,commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //http://localhost:8080/api/article/1/comments/2
    @PutMapping("/article/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("articleId") long articleId,
            @PathVariable("commentId") long commentId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto=commentService.updateComment(articleId,commentId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/article/{articleId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("articleId") long articleId,
            @PathVariable("commentId") long commentId
    ){
        commentService.deleteComment(articleId,commentId);
        return new ResponseEntity<>("Comment has been deleted.!!!",HttpStatus.OK);
    }
}
