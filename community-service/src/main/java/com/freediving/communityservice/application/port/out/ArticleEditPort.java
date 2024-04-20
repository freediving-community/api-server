package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.domain.Article;

public interface ArticleEditPort {

	Long updateArticle(Article changedArticle);

	int increaseLikeCount(BoardType boardType, Long articleId);

	int decreaseLikeCount(BoardType boardType, Long articleId);

	int increaseViewCount(BoardType boardType, Long articleId);

}
