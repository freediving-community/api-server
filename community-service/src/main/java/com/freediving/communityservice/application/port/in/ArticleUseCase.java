package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;

public interface ArticleUseCase {

	// Query
	ArticleContent getArticle(ArticleReadCommand articleReadCommand);

	ArticleContentWithComment getArticleWithComment(Long boardId, Long articleId);

	// Command
	Long writeArticle(ArticleWriteCommand articleWriteCommand);
}
