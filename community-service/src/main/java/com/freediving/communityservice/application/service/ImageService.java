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

	// private final AwsImageUploadPort awsImageUploadPort;
	private final ImageWritePort imageWritePort;

	@Override
	public String getPresignedUrl(ImageUploadCommand command) {
		Long userId = command.getUserProvider().getRequestUserId();

		//TODO 필요한 값으로 조작 후 Member-Service에 presigned URL 요청
		String presignedURL = "";
		String parsedURL = "";
		Long imageId = imageWritePort.saveImageTemporary(parsedURL);
		// awsImageUploadPort.generatePresignedUrl();
		return presignedURL;
	}

}
