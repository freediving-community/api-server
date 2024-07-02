package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.domain.Article;

public interface ArticleEditPort {

	Long updateArticle(Article changedArticle);

	int increaseViewCount(BoardType boardType, Long articleId);

	int increaseLikeCount(BoardType boardType, Long articleId);

	int decreaseLikeCount(BoardType boardType, Long articleId);

	int increaseCommentCount(BoardType boardType, Long articleId);

	int decreaseCommentCount(BoardType boardType, Long articleId);

}
