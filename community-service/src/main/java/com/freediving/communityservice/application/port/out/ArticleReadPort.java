package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.domain.Article;

public interface ArticleReadPort {
	Article readArticle(Long boardId, Long articleId, boolean isShowAll);

	ArticleContentWithComment readArticleWithComment(ArticleReadCommand articleReadCommand);

}
