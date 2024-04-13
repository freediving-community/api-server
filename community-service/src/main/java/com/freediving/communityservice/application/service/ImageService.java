package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.application.port.in.ImageUploadCommand;
import com.freediving.communityservice.application.port.in.ImageUseCase;
import com.freediving.communityservice.application.port.out.ImageWritePort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService implements ImageUseCase {

	private final ImageWritePort imageWritePort;

	@Override
	public String saveImages(ImageUploadCommand command) {
		// 2개의 테이블에 동일한 N개만큼의 INSERT 수행. 나뉘어져야 함.
		// 1. imageRepository에 저장한다.
		// 2. article_image 매핑 테이블에 저장한다.

		return "";
	}
}
