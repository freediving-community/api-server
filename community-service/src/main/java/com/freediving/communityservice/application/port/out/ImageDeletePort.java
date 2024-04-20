package com.freediving.communityservice.application.port.out;

import java.util.List;

public interface ImageDeletePort {
	void deleteAllByArticleId(Long articleId);

	void deleteAllByArticleIdAndUrlIn(Long articleId, List<String> urls);
}
