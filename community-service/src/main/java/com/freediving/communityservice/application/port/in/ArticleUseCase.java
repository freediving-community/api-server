package com.freediving.communityservice.application.port.in;

import org.springframework.data.domain.Page;

import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefResponse;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithCommentResponse;

public interface ArticleUseCase {

	// Query
	// Article getArticle(ArticleReadCommand articleReadCommand);

	ArticleContentWithCommentResponse getArticleWithComment(ArticleReadCommand articleReadCommand);

	Page<ArticleBriefResponse> getArticleIndexList(ArticleIndexListCommand indexListCommand);

	// Command

	Long writeArticle(ArticleWriteCommand articleWriteCommand);

	Long deleteArticle(ArticleRemoveCommand articleRemoveCommand);

	Long editArticle(ArticleEditCommand build);
}
