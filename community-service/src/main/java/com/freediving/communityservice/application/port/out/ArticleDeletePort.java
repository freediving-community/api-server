package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.application.port.in.ArticleRemoveCommand;

public interface ArticleDeletePort {
	Long markDeleted(ArticleRemoveCommand articleRemoveCommand);
}
