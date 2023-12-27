package com.freediving.community.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freediving.community.application.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
