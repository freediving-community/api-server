package com.freediving.communityservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleJpaEntity, Long> {
}
