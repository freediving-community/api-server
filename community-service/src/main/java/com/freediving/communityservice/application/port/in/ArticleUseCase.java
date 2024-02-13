package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.domain.Article;

public interface ArticleUseCase {

	// Query
	Article getArticle(ArticleReadCommand articleReadCommand);

	ArticleContentWithComment getArticleWithComment(ArticleReadCommand articleReadCommand);

	// Command
	Long writeArticle(ArticleWriteCommand articleWriteCommand);

	Long deleteArticle(ArticleRemoveCommand articleRemoveCommand);
}
