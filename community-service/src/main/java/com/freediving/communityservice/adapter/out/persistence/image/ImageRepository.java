package com.freediving.communityservice.adapter.out.persistence.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;

public interface ImageRepository extends JpaRepository<ImageJpaEntity, Long> {

	List<ImageResponse> findByArticleIdAndDeletedAtIsNullOrderBySortNumber(Long articleId);

	void deleteAllByArticleId(Long articleId);

	void deleteAllByArticleIdAndUrlIn(Long articleId, List<String> urls);

	List<ImageJpaEntity> findAllByArticleIdAndUrlIn(Long articleId, List<String> urls);
}
