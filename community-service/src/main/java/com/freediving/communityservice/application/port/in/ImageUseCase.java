package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.domain.Article;

public interface ImageUseCase {

	String getPresignedUrl(ImageUploadCommand command);
}
