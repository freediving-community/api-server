package com.freediving.communityservice.application.port.out;

public interface ImageDeletePort {
	void deleteAllByArticleId(Long articleId);
}
