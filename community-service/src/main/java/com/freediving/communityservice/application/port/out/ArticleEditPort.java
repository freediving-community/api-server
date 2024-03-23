package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

public interface ArticleEditPort {

	Long updateArticle(BoardType boardType, Long articleId, String title, String content, List<Long> hashtagIds,
		boolean enableComment);

	int increaseLikeCount(BoardType boardType, Long articleId);

	int decreaseLikeCount(BoardType boardType, Long articleId);

	int increaseViewCount(BoardType boardType, Long articleId);

}
