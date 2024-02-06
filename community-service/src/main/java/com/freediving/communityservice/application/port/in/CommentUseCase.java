package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.comment.CommentResponse;
import com.freediving.communityservice.domain.Comment;

public interface CommentUseCase {
	Comment writeComment(CommentWriteCommand command);
}
