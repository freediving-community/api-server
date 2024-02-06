package com.freediving.communityservice.adapter.out.persistence.comment;

import java.util.Optional;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.application.port.out.CommentReadPort;
import com.freediving.communityservice.application.port.out.CommentWritePort;
import com.freediving.communityservice.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentWritePort, CommentReadPort {

	private final CommentRepository commentRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final CommentPersistenceMapper commentMapper;

	public Comment findById(CommentReadCommand command) {
		Optional<CommentJpaEntity> foundComment = commentRepository.findById(command.getCommentId());
		CommentJpaEntity commentJpaEntity = foundComment.orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));

		return commentMapper.mapToDomain(commentJpaEntity);
	}

	@Override
	public Comment readComment(CommentReadCommand command) {
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
}
