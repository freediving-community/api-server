package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.domain.Comment;

public interface CommentUseCase {
	Comment writeComment(CommentWriteCommand command);

	Comment readComments(CommentReadCommand command);
}
