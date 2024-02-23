package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.application.port.in.ImageUploadCommand;
import com.freediving.communityservice.application.port.in.ImageUseCase;
import com.freediving.communityservice.application.port.out.infra.AwsImageUploadPort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService implements ImageUseCase {

	private final AwsImageUploadPort awsImageUploadPort;

	@Override
	public String getPresignedUrl(ImageUploadCommand command) {

		// awsImageUploadPort.generatePresignedUrl();
		return null;
	}
}
