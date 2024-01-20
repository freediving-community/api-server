package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.domain.Article;

public interface ArticleWritePort {
	Article writeArticle(ArticleWriteCommand articleWriteCommand);
}
