package com.freediving.communityservice.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefDto;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.domain.Article;

public interface ArticleReadPort {
	Article readArticle(BoardType boardType, Long articleId, boolean isShowAll);

	// ArticleContentWithComment readArticleWithComment(BoardType boardType, Long articleId, boolean isShowAll, UserProvider requestUser);

	Page<ArticleBriefDto> retrieveArticleIndexList(BoardType boardType, Long cursor, Pageable pageable);
}
