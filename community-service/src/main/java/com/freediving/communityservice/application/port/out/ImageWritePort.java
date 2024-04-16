package com.freediving.communityservice.application.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;

public interface ImageWritePort {
	int saveImages(Long articleId, Long createdBy, LocalDateTime createdAt, List<ImageInfoCommand> command);
}
