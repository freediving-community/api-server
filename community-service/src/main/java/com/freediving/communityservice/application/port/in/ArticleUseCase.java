package com.freediving.communityservice.application.port.in;

import org.springframework.data.domain.Page;

import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefDto;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.domain.Article;

public interface ArticleUseCase {

	// Query
	Article getArticle(ArticleReadCommand articleReadCommand);

	ArticleContent getArticleWithComment(ArticleReadCommand articleReadCommand);

	Page<ArticleBriefDto> getArticleIndexList(ArticleIndexListCommand indexListCommand);

	// Command

	Long writeArticle(ArticleWriteCommand articleWriteCommand);

	Long deleteArticle(ArticleRemoveCommand articleRemoveCommand);

	Long editArticle(ArticleEditCommand build);
}
