package com.freediving.communityservice.application.port.in;

import java.util.List;

import com.freediving.communityservice.domain.Comment;

public interface CommentUseCase {
	Comment writeComment(CommentWriteCommand command);

	Comment readComments(CommentReadCommand command);

	Comment editComment(CommentEditCommand command);

	List<Comment> getNextCommentsByLastCommentId(CommentReadCommand command);
}
