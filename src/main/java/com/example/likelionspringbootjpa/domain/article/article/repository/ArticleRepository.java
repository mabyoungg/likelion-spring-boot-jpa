package com.example.likelionspringbootjpa.domain.article.article.repository;

import com.example.likelionspringbootjpa.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByOrderByIdDesc();
}
