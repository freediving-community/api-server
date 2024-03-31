package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.domain.Comment;

public interface CommentReadPort {
	Comment findById(CommentReadCommand command);

	int getCommentCountOfArticle(Long articleId);

	List<Comment> getNextCommentsByLastCommentId(Long articleId, Long commentId, Long requestUserId);

	List<Comment> readDefaultComments(BoardType boardType, Long articleId, Long createdBy, Long requestUserId);

	List<Comment> readRepliesByCommentId(Long articleId, List<Long> validCommentIds);
}
