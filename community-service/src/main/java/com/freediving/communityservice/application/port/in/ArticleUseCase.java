package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.external.article.ArticleContent;
import com.freediving.communityservice.adapter.out.external.article.ArticleContentWithComment;

public interface ArticleUseCase {
	// Query
	ArticleContent getArticle(String boardId, String articleId);

	ArticleContentWithComment getArticleWithComment(String boardId, String articleId);

	// Command
}
