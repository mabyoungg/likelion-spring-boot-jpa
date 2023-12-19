package com.example.likelionspringbootjpa.domain.article.article.repository;

import com.example.likelionspringbootjpa.domain.article.article.entity.Article;
import com.example.likelionspringbootjpa.domain.article.articleComment.entity.QArticleComment;
import com.example.likelionspringbootjpa.domain.article.articleTag.entity.QArticleTag;
import com.example.likelionspringbootjpa.domain.member.member.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.likelionspringbootjpa.domain.article.article.entity.QArticle.article;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        /*
        # 한 페이지에 게시물 `10`개까지 보여줄 수 있고, 현재 `2` 페이지 이고, 검색어가 `1` 이라고 가정
        SELECT DISTINCT A.*
        FROM article AS A
        LEFT JOIN `member` AS AM
        ON A.author_id = AM.id
        LEFT JOIN article_comment AS AC
        ON A.id = AC.article_id
        LEFT JOIN `member` AS ACM
        ON AC.author_id = ACM.id
        LEFT JOIN article_tag AS ATG
        ON A.id = ATG.article_id
        WHERE A.title LIKE '%1%'
        OR A.body LIKE '%1%'
        OR AM.username LIKE '%1%'
        OR AC.body LIKE '%1%'
        OR ACM.username LIKE '%1%'
        OR ATG.content = '1'
        ORDER BY A.id DESC
        LIMIT 10, 10;
        */
        BooleanBuilder builder = new BooleanBuilder();

        QMember author = new QMember("articleAuthor");
        QArticleTag articleTag = new QArticleTag("articleTag");
        QArticleComment articleComment = new QArticleComment("articleComment");
        QMember articleCommentAuthor = new QMember("articleCommentAuthor");

        if (!kw.isBlank()) {
            // 기존의 조건을 리스트에 담는다.
            List<BooleanExpression> conditions = new ArrayList<>();

            if (kwTypes.contains("authorUsername")) {
                conditions.add(author.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("title")) {
                conditions.add(article.title.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("body")) {
                conditions.add(article.body.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("tagContent")) {
                conditions.add(articleTag.content.eq(kw));
            }

            if (kwTypes.contains("commentAuthorUsername")) {
                conditions.add(articleCommentAuthor.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("commentBody")) {
                conditions.add(articleComment.body.containsIgnoreCase(kw));
            }

            // 조건 리스트를 or 조건으로 결합
            BooleanExpression combinedCondition = conditions.stream()
                    .reduce(BooleanExpression::or)
                    .orElse(null);

            // 최종적으로 생성된 조건을 쿼리에 적용
            if (combinedCondition != null) {
                builder.and(combinedCondition);
            }
        }

        JPAQuery<Article> articlesQuery = jpaQueryFactory
                .selectDistinct(article)
                .from(article)
                .leftJoin(article.author, author)
                .leftJoin(article.comments, articleComment)
                .leftJoin(articleComment.author, articleCommentAuthor)
                .leftJoin(article.tags, articleTag)
                .where(builder);

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(article.getType(), article.getMetadata());
            articlesQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        articlesQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        /*
        # 검색어가 `1` 이라고 가정
        SELECT COUNT(DISTINCT A.id)
        FROM article AS A
        LEFT JOIN `member` AS AM
        ON A.author_id = AM.id
        LEFT JOIN article_comment AS AC
        ON A.id = AC.article_id
        LEFT JOIN `member` AS ACM
        ON AC.author_id = ACM.id
        LEFT JOIN article_tag AS ATG
        ON A.id = ATG.article_id
        WHERE A.title LIKE '%1%'
        OR A.body LIKE '%1%'
        OR AM.username LIKE '%1%'
        OR AC.body LIKE '%1%'
        OR ACM.username LIKE '%1%'
        OR ATG.content = '1';
        */

        JPAQuery<Long> totalQuery = jpaQueryFactory
                .select(article.countDistinct())
                .from(article)
                .leftJoin(article.author, author)
                .leftJoin(article.comments, articleComment)
                .leftJoin(articleComment.author, articleCommentAuthor)
                .leftJoin(article.tags, articleTag)
                .where(builder);

        return PageableExecutionUtils.getPage(articlesQuery.fetch(), pageable, totalQuery::fetchOne);
    }
}