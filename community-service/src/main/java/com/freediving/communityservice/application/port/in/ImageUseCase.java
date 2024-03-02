package com.freediving.communityservice.application.port.in;

public interface ImageUseCase {

	String getPresignedUrl(ImageUploadCommand command);

}
