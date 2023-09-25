package com.Articles.security;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends Throwable {
    public BlogAPIException(HttpStatus httpStatus, String s) {
    }
}
