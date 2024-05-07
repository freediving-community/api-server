package com.freediving.communityservice.adapter.out.persistence.comment;

import static com.freediving.communityservice.adapter.out.persistence.comment.QCommentJpaEntity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
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
public class CommentPersistenceAdapter
	implements CommentWritePort, CommentReadPort, CommentEditPort, CommentDeletePort {

	private final CommentRepository commentRepository;
	private final CommentPersistenceMapper commentMapper;
	private final JPAQueryFactory jpaQueryFactory;
	private final JdbcClient jdbcClient;

	@Override
	public Comment findById(CommentReadCommand command) {
		CommentJpaEntity foundCommentEntity = commentRepository.findById(command.getCommentId()).orElseThrow(
			() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));

		return commentMapper.mapToDomain(foundCommentEntity);
	}

	@Override
	public int getCommentCountOfArticle(Long articleId) {
		return jdbcClient.sql("""
				SELECT COUNT(*) 
				FROM COMMENT 
				WHERE ARTICLE_ID = ?
				AND DELETED_AT IS NULL
				AND PARENT_ID IS NULL
				""")
			.param(1, articleId)
			.query(Integer.class)
			.single();
	}

	public List<Comment> getNextCommentsByLastCommentId(Long articleId, Long commentId, Long requestUserId) {
		StringBuffer sql = new StringBuffer("""
			SELECT * FROM (
			 SELECT * FROM COMMENT
			 WHERE ARTICLE_ID = :articleId
				AND DELETED_AT IS NULL
				AND PARENT_ID IS NULL
				AND COMMENT_ID < :commentId 
			""");
		if (!requestUserId.equals(-1L)) {
			sql.append(" AND CREATED_BY <> :requestUserId ");
		}

		sql.append(""" 
			 ORDER BY CREATED_AT DESC
			) as subquery1 LIMIT 2 
			""");

		return jdbcClient
			.sql(sql.toString())
			.param("articleId", articleId)
			.param("commentId", commentId)
			.param("requestUserId", requestUserId)
			.query(CommentJpaEntity.class)
			.list()
			.stream()
			.map(commentMapper::mapToDomain)
			.toList();
	}

	@Override
	public List<Comment> readDefaultComments(BoardType boardType, Long articleId, Long createdBy, Long requestUserId) {

		boolean userPriorityComments = false;

		if (!requestUserId.equals(-1L)) {
			if (boardType.equals(BoardType.GENERAL) || boardType.equals(BoardType.BUDDY_QNA)) {
				userPriorityComments = true;
			}
		}

		//TODO 리팩토링 및 쿼리 개선 - H2와 JdbcClient에서 WITH절 포함시 동작 이상
		StringBuffer sql = new StringBuffer();

		if (userPriorityComments) {
			sql.append("""
				SELECT * 
				FROM (
					SELECT *
					FROM COMMENT
					WHERE ARTICLE_ID = :articleId
					AND DELETED_AT IS NULL
					AND PARENT_ID IS NULL
					AND CREATED_BY = :requestUserId
					ORDER BY CREATED_AT DESC
				) as request_user_comment
				UNION ALL
				SELECT * 
				FROM (
					SELECT *
					FROM COMMENT
					WHERE ARTICLE_ID = :articleId
					AND DELETED_AT IS NULL
					AND PARENT_ID IS NULL
					AND CREATED_BY <> :requestUserId
					ORDER BY CREATED_AT DESC
					LIMIT 10
				) as other_comment
				"""
			);
		} else {
			sql.append("""
					SELECT *
					FROM COMMENT
					WHERE ARTICLE_ID = :articleId
					AND DELETED_AT IS NULL
					AND PARENT_ID IS NULL
					AND CREATED_BY <> :requestUserId
					ORDER BY CREATED_AT DESC
					LIMIT 10
				"""
			);
		}
		List<CommentJpaEntity> articleComments = jdbcClient
			.sql(sql.toString())
			.param("articleId", articleId)
			.param("requestUserId", requestUserId)
			.query(CommentJpaEntity.class)
			.list();

		return articleComments.stream()
			.map(commentMapper::mapToDomain)
			.toList();
	}

	@Override
	public List<Comment> readRepliesByCommentId(Long articleId, List<Long> validCommentIds) {
		List<CommentJpaEntity> repliesComment = jpaQueryFactory
			.selectFrom(commentJpaEntity)
			.where(
				commentJpaEntity.articleId.eq(articleId),
				commentJpaEntity.deletedAt.isNull(),
				commentJpaEntity.parentId.in(validCommentIds)
			)
			.orderBy(commentJpaEntity.createdAt.desc())
			.fetch();

		List<CommentJpaEntity> sortedRepliesComment = new ArrayList<>();

		validCommentIds.forEach(id -> {
			repliesComment.forEach(replyComment -> {
				if (id.equals(replyComment.getParentId()))
					sortedRepliesComment.add(replyComment);
			});
		});

		return sortedRepliesComment.stream()
			.map(commentMapper::mapToDomain)
			.toList();
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
	public Comment editComment(CommentEditCommand command) {
		CommentJpaEntity commentJpa = commentRepository.findById(command.getCommentId())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));
		commentJpa.editComment(command.getContent()/*, command.isVisible()*/);

		return commentMapper.mapToDomain(commentJpa);
	}

	@Override
	public void markDeletedByArticleId(Long articleId) {
		LocalDateTime deletedAt = LocalDateTime.now();
		commentRepository.markDeletedByArticleId(articleId, deletedAt);
	}

	@Override
	public void markDeletedWithReply(Long commentId) {
		LocalDateTime deletedAt = LocalDateTime.now();
		commentRepository.markDeletedByParentId(commentId, deletedAt);
	}

	@Override
	public void markDeleted(Long commentId) {
		LocalDateTime deletedAt = LocalDateTime.now();
		CommentJpaEntity commentJpa = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));
		commentJpa.markDeleted(deletedAt);
	}
}
