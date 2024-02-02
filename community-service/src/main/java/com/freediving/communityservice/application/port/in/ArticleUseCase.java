package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;

public interface ArticleUseCase {

	// Query
	ArticleContentWithComment getArticle(Long userId, ArticleReadCommand articleReadCommand);

	ArticleContentWithComment getArticleWithComment(Long boardId, Long articleId);

	// Command
	Long writeArticle(ArticleWriteCommand articleWriteCommand);
}
