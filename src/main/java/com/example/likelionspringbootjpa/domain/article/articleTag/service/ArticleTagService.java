package com.example.likelionspringbootjpa.domain.article.articleTag.service;

import com.example.likelionspringbootjpa.domain.article.articleTag.entity.ArticleTag;
import com.example.likelionspringbootjpa.domain.article.articleTag.repository.ArticleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleTagService {
    private final ArticleTagRepository articleTagRepository;

    public List<ArticleTag> findByAuthorId(long authorId) {
        return articleTagRepository.findByArticle_authorId(authorId);
    }

    public List<ArticleTag> findByAuthorUsername(String username) {
        return articleTagRepository.findByArticle_author_username(username);
    }
}
