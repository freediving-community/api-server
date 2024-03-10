package com.freediving.communityservice.adapter.out.persistence.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<ArticleJpaEntity, Long> {

	@Modifying
	@Query(value = "UPDATE ARTICLE SET LIKE_COUNT = LIKE_COUNT + 1 WHERE BOARD_TYPE = :boardTypeName AND ARTICLE_ID = :articleId", nativeQuery = true)
	int increaseLikeCount(@Param("boardTypeName") String boardTypeName, @Param("articleId") Long articleId);

	@Modifying
	@Query(value = "UPDATE ARTICLE SET LIKE_COUNT = LIKE_COUNT - 1 WHERE BOARD_TYPE = :boardTypeName AND ARTICLE_ID = :articleId", nativeQuery = true)
	int decreaseLikeCount(@Param("boardTypeName") String boardTypeName, @Param("articleId") Long articleId);

	@Modifying
	@Query(value = "UPDATE ARTICLE SET VIEW_COUNT = VIEW_COUNT + 1 WHERE BOARD_TYPE = :boardTypeName AND ARTICLE_ID = :articleId", nativeQuery = true)
	int increaseViewCount(@Param("boardTypeName") String boardTypeName, @Param("articleId") Long articleId);
}
