package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.domain.Comment;

public interface CommentReadPort {
	Comment readComment(CommentReadCommand command);
}
