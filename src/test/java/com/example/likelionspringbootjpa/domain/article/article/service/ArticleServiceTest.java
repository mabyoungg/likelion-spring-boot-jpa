package com.example.likelionspringbootjpa.domain.article.article.service;

import com.example.likelionspringbootjpa.domain.article.article.entity.Article;
import com.example.likelionspringbootjpa.domain.member.member.entity.Member;
import com.example.likelionspringbootjpa.domain.member.member.service.MemberService;
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
    @Autowired
    private MemberService memberService;

    @DisplayName("article write")
    @Test
    void t1() {
        RsData<Article> joinRs = articleService.write(1, "제목", "내용");
        Article article = joinRs.getData();

        assertThat(article.getId()).isGreaterThan(0L);
    }

    @DisplayName("1번 글의 작성자 username 은 user1")
    @Test
    void t2() {
        Article article = articleService.findById(1L).get();
        Member author = article.getAuthor();

        assertThat(author.getUsername()).isEqualTo("user1");
    }
}
