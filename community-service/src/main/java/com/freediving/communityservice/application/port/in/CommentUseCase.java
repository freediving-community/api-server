package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.comment.CommentResponse;

public interface CommentUseCase {
	CommentResponse writeComment(CommentWriteCommand command);
}
