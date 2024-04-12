package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;
import com.freediving.communityservice.domain.Article;

public interface ImageWritePort {
	int saveImages(Article article, List<ImageInfoCommand> command);
}
