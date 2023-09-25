package com.Articles.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private long id;
    private String type;

    private String title;

    private String description;
}
