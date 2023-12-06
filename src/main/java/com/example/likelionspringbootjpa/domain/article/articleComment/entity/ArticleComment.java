package com.example.likelionspringbootjpa.domain.article.articleComment.entity;

import com.example.likelionspringbootjpa.domain.article.article.entity.Article;
import com.example.likelionspringbootjpa.domain.member.member.entity.Member;
import com.example.likelionspringbootjpa.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class ArticleComment extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Article article;
    @ManyToOne(fetch = LAZY)
    private Member author;
    private String body;
}
