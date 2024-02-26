package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.domain.Article;

public interface ArticleEditPort {

	Long updateArticle(Long boardId, Long articleId, String title, String content, List<Long> hashtagIds, boolean enableComment);
}
