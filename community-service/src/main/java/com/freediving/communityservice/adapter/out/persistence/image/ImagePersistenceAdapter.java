package com.freediving.communityservice.adapter.out.persistence.image;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;
import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;
import com.freediving.communityservice.application.port.out.ImageDeletePort;
import com.freediving.communityservice.application.port.out.ImageEditPort;
import com.freediving.communityservice.application.port.out.ImageReadPort;
import com.freediving.communityservice.application.port.out.ImageWritePort;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ImagePersistenceAdapter implements ImageWritePort, ImageReadPort, ImageEditPort, ImageDeletePort {

	private final ImageRepository imageRepository;

	@Override
	public int saveImages(Long articleId, Long createdBy, LocalDateTime createdAt, List<ImageInfoCommand> command) {

		List<ImageJpaEntity> images = command.stream().map(
				image -> ImageJpaEntity.builder()
					.articleId(articleId)
					.url(image.getUrl())
					.sortNumber(image.getSortNumber())
					.createdBy(createdBy)
					.createdAt(createdAt)
					.build()
			)
			.toList();

		List<ImageJpaEntity> imageJpaEntities = imageRepository.saveAll(images);

		return imageJpaEntities.size();
	}

	@Override
	public List<ImageResponse> getImageListByArticle(Long articleId) {
		return imageRepository.findByArticleIdAndDeletedAtIsNullOrderBySortNumber(articleId);
	}

	@Override
	public void deleteAllByArticleId(Long articleId) {
		imageRepository.deleteAllByArticleId(articleId);
	}

	@Override
	public void deleteAllByArticleIdAndUrlIn(Long articleId, List<String> urls) {
		imageRepository.deleteAllByArticleIdAndUrlIn(articleId, urls);
	}

	@Override
	public List<ImageResponse> editAllImageSortNumber(Long articleId, Map<String, ImageResponse> imageMapToUpdate) {

		List<ImageJpaEntity> updateImages = imageRepository.findAllByArticleIdAndUrlIn(
			articleId,
			imageMapToUpdate.keySet().stream().toList()
		);

		updateImages.forEach(image -> {
			image.changeSortNumber(
				imageMapToUpdate.get(image.getUrl()).sortNumber()
			);
		});
		imageRepository.flush();
		return updateImages.stream()
			.map(
				imageJpaEntity -> new ImageResponse(imageJpaEntity.getSortNumber(), imageJpaEntity.getUrl())
			)
			.toList();
	}
}
