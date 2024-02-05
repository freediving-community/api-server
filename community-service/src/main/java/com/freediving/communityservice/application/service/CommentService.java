package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.dto.comment.CommentResponse;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.BoardReadPort;
import com.freediving.communityservice.application.port.out.CommentReadPort;
import com.freediving.communityservice.application.port.out.CommentWritePort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService implements CommentUseCase {

	private final BoardReadPort boardReadPort;
	private final ArticleReadPort articleReadPort;
	private final CommentReadPort commentReadPort;
	private final CommentWritePort commentWritePort;

	@Override
	public CommentResponse writeComment(CommentWriteCommand command) {
		Board board = boardReadPort.findById(command.getBoardId());
		board.checkPermission(command);
		Article article = articleReadPort.readArticle(ArticleReadCommand.builder()
			.boardId(board.getId())
			.articleId(command.getArticleId())
			.isEnabledOnly(true)
			.withoutComment(true)
			.build());

		// commentWritePort.writeComment();
		return null;
	}
}
