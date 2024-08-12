package com.freediving.communityservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.freediving.communityservice.application.port.in.CommentDeleteCommand;
import com.freediving.communityservice.application.port.in.CommentEditCommand;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleEditPort;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.CommentDeletePort;
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
	private final ArticleEditPort articleEditPort;
	private final CommentReadPort commentReadPort;
	private final CommentWritePort commentWritePort;
	private final CommentEditPort commentEditPort;
	private final CommentDeletePort commentDeletePort;

	@Override
	public Comment writeComment(CommentWriteCommand command) {
		Article article = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);
		article.checkCommentEnabled();

		if (command.hasParentComment()) {
			/*
			 * 답글을 달려고 할 때, 비밀글이면 *[게시글 작성자, 최초 댓글 작성자]만 달 수 있다.
			 * 최초 댓글이 비밀글이면, 이후 자동으로 답글은 모두 비밀글
			 * 최초 댓글이 비밀글인데, *미권한자가 API 작성 요청 불가 checkCommentVisible
			 * */
			Comment firstParentComment = commentReadPort.findById(CommentReadCommand.builder()
				.commentId(command.getParentId())
				.build());
			firstParentComment.checkParentComment();
			firstParentComment.checkCommentVisible(command.getRequestUser(), firstParentComment.getCreatedBy(),
				article.getCreatedBy());

			CommentWriteCommand forcedVisibleComment = command.applyParentVisible(command,
				firstParentComment.isVisible());

			return commentWritePort.writeComment(forcedVisibleComment);
		}

		article.increaseCommentCount();
		articleEditPort.increaseCommentCount(article.getBoardType(), article.getId());

		return commentWritePort.writeComment(command);
	}

	// TODO: 더보기.. 기능 구현 필요시 작성
	@Override
	public Comment readComments(CommentReadCommand command) {
/*		Article article = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), true);
		article.canCreateComment();

		commentReadPort.readComments(command);*/
		return null;
	}

	@Override
	public Comment editComment(CommentEditCommand command) {

		// TODO: 숨김 상태는 수정 불가
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

	@Override
	public Long removeComment(CommentDeleteCommand command) {

		Article article = articleReadPort.readArticle(
			command.getBoardType(),
			command.getArticleId(),
			false
		);
		Comment comment = commentReadPort.findById(CommentReadCommand.builder()
			.commentId(command.getCommentId())
			.build());

		comment.checkCommentOwner(command.getRequestUser().getRequestUserId());

		if (ObjectUtils.isEmpty(comment.getParentId())) {
			// 댓글을 삭제하는 경우 (parent_id가 해당 댓글을 보고 있는) 하위 답글까지 삭제
			commentDeletePort.markDeletedWithReply(comment.getCommentId());
		} else {
			// 답글인 경우 단건 삭제
			commentDeletePort.markDeleted(comment.getCommentId());
		}

		article.decreaseCommentCount();
		articleEditPort.decreaseCommentCount(article.getBoardType(), article.getId());

		return command.getCommentId();
	}

}
