package com.freediving.communityservice.adapter.out.persistence.image;

import java.util.List;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;
import com.freediving.communityservice.application.port.out.ImageWritePort;
import com.freediving.communityservice.domain.Article;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ImagePersistenceAdapter implements ImageWritePort {

	private final ImageRepository imageRepository;

	@Override
	public int saveImages(Article article, List<ImageInfoCommand> command) {

		List<ImageJpaEntity> images = command.stream().map(
				image -> ImageJpaEntity.builder()
					.articleId(article.getId())
					.url(image.getUrl())
					.sortNumber(image.getSortNumber())
					.size(image.getSize())
					.extension(image.getExtension())
					.createdBy(article.getCreatedBy())
					.createdAt(article.getCreatedAt())
					.build()
			)
			.toList();

		List<ImageJpaEntity> imageJpaEntities = imageRepository.saveAll(images);

		return imageJpaEntities.size();
	}
}
