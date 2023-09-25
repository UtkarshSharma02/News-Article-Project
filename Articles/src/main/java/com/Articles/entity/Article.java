package com.Articles.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="articles")
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="type",nullable = false)
    private String type;

    @Column(name="title",nullable = false,unique=true)
    private String title;

    @Column(name="description",nullable = false)
    private String description;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<Comment> comments;
}
