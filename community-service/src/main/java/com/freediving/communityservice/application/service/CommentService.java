package com.freediving.communityservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.application.port.in.CommentEditCommand;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.CommentEditPort;
import com.freediving.communityservice.application.port.out.CommentReadPort;
import com.freediving.communityservice.application.port.out.CommentWritePort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService implements CommentUseCase {

	private final ArticleReadPort articleReadPort;
	private final CommentReadPort commentReadPort;
	private final CommentWritePort commentWritePort;
	private final CommentEditPort commentEditPort;

	@Override
	public Comment writeComment(CommentWriteCommand command) {
		Article article = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), true);

		if (command.hasParentComment()) {
			/*
			 * 답글을 달려고 할 때, 비밀글이면 *[게시글 작성자, 최초 댓글 작성자]만 달 수 있다.
			 * 최초 댓글이 비밀글이면, 이후 자동으로 답글은 모두 비밀글
			 * 최초 댓글이 비밀글인데, *미권한자가 API 작성 요청 불가 checkCommentVisible
			 * */
			Comment firstParentComment = commentReadPort.findById(CommentReadCommand.builder()
				.commentId(command.getParentId())
				.build());
			firstParentComment.isParentComment();
			firstParentComment.checkCommentVisible(command.getRequestUser(), firstParentComment.getCreatedBy(),
				article.getCreatedBy());

			CommentWriteCommand forcedVisibleComment = command.applyParentVisible(command,
				firstParentComment.isVisible());

			return commentWritePort.writeComment(forcedVisibleComment);
		}

		return commentWritePort.writeComment(command);
	}

	@Override
	public Comment readComments(CommentReadCommand command) {
/*		Article article = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), true);
		article.canCreateComment();

		commentReadPort.readComments(command);*/
		return null;
	}

	@Override
	public Comment editComment(CommentEditCommand command) {
		Comment comment = commentReadPort.findById(CommentReadCommand.builder()
			.commentId(command.getCommentId())
			.build());
		comment.checkCommentOwner(command.getRequestUser().getRequestUserId());
		return commentEditPort.editComment(command);
	}

	@Override
	public List<Comment> getNextCommentsByLastCommentId(CommentReadCommand command) {
		Article targetArticle = articleReadPort.readArticle(
			command.getBoardType(),
			command.getArticleId(),
			false
		);

		List<Comment> nextComments = commentReadPort.getNextCommentsByLastCommentId(
			targetArticle.getId(),
			command.getCommentId(),
			command.getRequestUser().getRequestUserId()
		);

		return nextComments.stream()
			.map(comment -> comment.processSecretComment(
				targetArticle.getBoardType(),
				targetArticle.getCreatedBy(),
				command.getRequestUser().getRequestUserId()
			)).toList();
	}

}
