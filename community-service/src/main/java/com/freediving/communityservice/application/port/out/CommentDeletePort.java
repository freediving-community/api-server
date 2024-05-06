package com.freediving.communityservice.application.port.out;

public interface CommentDeletePort {

	void markDeletedByArticleId(Long articleId);

	void markDeletedWithReply(Long commentId);

	void markDeleted(Long commentId);
}
