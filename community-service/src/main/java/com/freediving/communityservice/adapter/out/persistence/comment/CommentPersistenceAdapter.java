package com.freediving.communityservice.adapter.out.persistence.comment;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.application.port.out.CommentReadPort;
import com.freediving.communityservice.application.port.out.CommentWritePort;
import com.freediving.communityservice.domain.Comment;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentWritePort, CommentReadPort {
	@Override
	public Comment readComment(CommentReadCommand command) {
		return null;
	}

	@Override
	public Comment writeComment(CommentWriteCommand command) {
		return null;
	}
}
