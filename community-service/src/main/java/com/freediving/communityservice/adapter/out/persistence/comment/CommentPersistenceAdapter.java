package com.freediving.communityservice.adapter.out.persistence.comment;

import static com.freediving.communityservice.adapter.out.persistence.comment.QCommentJpaEntity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.in.CommentEditCommand;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.application.port.out.CommentDeletePort;
import com.freediving.communityservice.application.port.out.CommentEditPort;
import com.freediving.communityservice.application.port.out.CommentReadPort;
import com.freediving.communityservice.application.port.out.CommentWritePort;
import com.freediving.communityservice.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentWritePort, CommentReadPort, CommentEditPort, CommentDeletePort {

	private final CommentRepository commentRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final CommentPersistenceMapper commentMapper;

	public Comment findById(CommentReadCommand command) {
		CommentJpaEntity foundCommentEntity = commentRepository.findById(command.getCommentId()).orElseThrow(
			() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));

		return commentMapper.mapToDomain(foundCommentEntity);
	}

	@Override
	public Comment readComments(CommentReadCommand command) {
		return null;
	}

	@Override
	public Comment writeComment(CommentWriteCommand command) {
		CommentJpaEntity commentJpaEntity = commentRepository.save(
			CommentJpaEntity.builder()
				.articleId(command.getArticleId())
				.parentId(command.getParentId())
				.content(command.getContent())
				.visible(command.isVisible())
				.build()
		);
		return commentMapper.mapToDomain(commentJpaEntity);
	}

	@Override
	public void markDeleted(Long articleId) {
		// List<CommentJpaEntity> comments = jpaQueryFactory
		// 	.selectFrom(commentJpaEntity)
		// 	.where(
		// 		commentJpaEntity.articleId.eq(articleId)
		// 	).fetch();

		LocalDateTime deletedAt = LocalDateTime.now();
		commentRepository.markDeleted(articleId, deletedAt);
		// comments.forEach(c -> c.markDeleted(deletedAt));
	}

	@Override
	public Comment editComment(CommentEditCommand command) {
		CommentJpaEntity commentJpa = commentRepository.findById(command.getCommentId())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));
		commentJpa.editComment(command.getContent(), command.isVisible());

		return commentMapper.mapToDomain(commentJpa);
	}
}
