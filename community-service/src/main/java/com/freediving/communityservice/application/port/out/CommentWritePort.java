package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.domain.Comment;

public interface CommentWritePort {
	Comment writeComment(CommentWriteCommand command);

}
