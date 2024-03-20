package com.freediving.communityservice.adapter.out.persistence.comment;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CommentJpaEntity, Long> {
	@Modifying
	@Query(value = "UPDATE COMMENT SET DELETED_AT = :deletedAt WHERE ARTICLE_ID = :articleId", nativeQuery = true)
	int markDeleted(@Param("articleId") Long articleId, @Param("deletedAt")LocalDateTime deletedAt);
}
