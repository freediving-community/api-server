package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;

public interface ArticleReadPort {
	ArticleContentWithComment readArticle(ArticleReadCommand articleReadCommand);

}
