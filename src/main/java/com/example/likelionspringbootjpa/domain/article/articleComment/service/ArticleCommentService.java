//package com.example.likelionspringbootjpa.domain.article.articleComment.service;
//
//import com.example.likelionspringbootjpa.domain.article.article.entity.Article;
//import com.example.likelionspringbootjpa.domain.article.articleComment.entity.ArticleComment;
//import com.example.likelionspringbootjpa.domain.article.articleComment.repository.ArticleCommentRepository;
//import com.example.likelionspringbootjpa.domain.member.member.entity.Member;
//import com.example.likelionspringbootjpa.global.rsData.RsData;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ArticleCommentService {
//    private final ArticleCommentRepository articleCommentRepository;
//
//    public RsData<ArticleComment> write(Member author, Article article, String body) {
//        ArticleComment articleComment = ArticleComment.builder()
//                .author(author)
//                .article(article)
//                .body(body)
//                .build();
//
//        articleCommentRepository.save(articleComment);
//
//        return RsData.of("200", "%d번 댓글이 작성되었습니다.".formatted(articleComment.getId()), articleComment);
//    }
//
//    public Optional<ArticleComment> findLatest() {
//        return articleCommentRepository.findFirstByOrderByIdDesc();
//    }
//
//    @Transactional
//    public void modify(ArticleComment comment, String body) {
//        comment.setBody(body);
//    }
//
//    public Optional<ArticleComment> findFirstByArticleIdOrderByIdDesc(long l) {
//        return articleCommentRepository.findFirstByArticleIdOrderByIdDesc(l);
//    }
//
//    public void delete(ArticleComment comment) {
//        articleCommentRepository.delete(comment);
//    }
//}
