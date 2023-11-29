package com.example.likelionspringbootjpa.domain.article.article.service;

import com.example.likelionspringbootjpa.domain.article.article.entity.Article;
import com.example.likelionspringbootjpa.global.rsData.RsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @DisplayName("article write")
    @Test
    void t1() {
        RsData<Article> joinRs = articleService.write("제목", "내용");
        Article article = joinRs.getData();

        assertThat(article.getId()).isGreaterThan(0L);
    }
}
