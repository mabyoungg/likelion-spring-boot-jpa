package com.example.likelionspringbootjpa.domain.article.article.entity;

import com.example.likelionspringbootjpa.domain.article.articleComment.entity.ArticleComment;
import com.example.likelionspringbootjpa.domain.member.member.entity.Member;
import com.example.likelionspringbootjpa.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class Article extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member author;
    private String title;
    private String body;
    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<ArticleComment> comments = new ArrayList<>();

    public void addComment(Member commentAuthor, String commentBody) {
        ArticleComment comment = ArticleComment
                .builder()
                .article(this)
                .author(commentAuthor)
                .body(commentBody)
                .build();

        comments.add(comment);
    }

    public void removeComment(ArticleComment comment) {
        comments.remove(comment);
    }
}
