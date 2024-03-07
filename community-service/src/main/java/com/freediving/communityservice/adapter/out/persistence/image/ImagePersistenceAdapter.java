package com.freediving.communityservice.adapter.out.persistence.image;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.out.ImageWritePort;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ImagePersistenceAdapter implements ImageWritePort {

	// private final JPAQueryFactory jpaQueryFactory;
	private final ImageRepository imageRepository;

	@Override
	public Long saveImageTemporary(String parsedUrl) {
		ImageJpaEntity tempImage = imageRepository.save(
			ImageJpaEntity.builder()
				.imageServerId(parsedUrl)
				.build()
		);

		return tempImage.getId();
	}
}
