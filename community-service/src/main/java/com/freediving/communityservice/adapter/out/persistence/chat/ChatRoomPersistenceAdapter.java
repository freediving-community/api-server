package com.freediving.communityservice.adapter.out.persistence.chat;

import java.util.List;
import java.util.Optional;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.domain.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ChatRoomReadPort, ChatRoomCreationPort {

	private final ChatRoomRepository chatRoomRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final ChatRoomPersistenceMapper chatRoomMapper;
	// private final JdbcClient jdbcClient;

	@Override
	public ChatRoom getChatRoom(ChatType chatType, Long targetId) {
		Optional<ChatRoomJpaEntity> chatRoom = chatRoomRepository.findByChatTypeAndTargetId(chatType, targetId);
		return chatRoom.map(chatRoomMapper::mapToDomain).orElse(null);
	}

	@Override
	public List<ChatRoom> getChatRoomList(Long createdBy, Long chatRoomId) {
		return List.of();
	}

	@Override
	public ChatRoom createBuddyChatRoom(Long requestUserId, ChatType chatType, Long buddyEventId, String title,
		String openChatRoomURL) {
		return null;
	}
/*

	@Override
	public Article writeArticle(ArticleWriteCommand articleWriteCommand) {
		ArticleJpaEntity savedArticle = articleRepository.save(
			ArticleJpaEntity.of(
				articleWriteCommand.getTitle(),
				articleWriteCommand.getContent(),
				articleWriteCommand.getBoardType(),
				articleWriteCommand.isEnableComment()
			)
		);

		return articleMapper.mapToDomain(savedArticle);
	}

	@Override
	public Article readArticle(BoardType boardType, Long articleId, boolean isShowAll) {
		ArticleJpaEntity foundArticle = jpaQueryFactory
			.selectFrom(articleJpaEntity)
			.where(
				boardTypeEq(boardType),
				articleIdEq(articleId),
				isShowAll ?
					null : articleJpaEntity.deletedAt.isNull()
			).fetchOne();

		if (foundArticle == null) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "해당하는 게시글이 없습니다.");
		}
		return articleMapper.mapToDomain(foundArticle);
	}

	// @Override
	// public ArticleContentWithComment readArticleWithComment(BoardType boardType, Long articleId, boolean isShowAll,
	// 	UserProvider requestUser) {
	//
	// 	Article foundArticle = readArticle(boardType, articleId, isShowAll);
	//
	// 	List<Comment> comments = commentReadPort.
	// 	return new ArticleContentWithComment(foundArticle, comments, false);
	// }

	@Override
	public Page<ArticleBriefResponse> retrieveArticleIndexList(BoardType boardType, Long cursor, boolean onlyPicture,
		Long userId, Pageable pageable) {

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("boardType", boardType.name());
		paramMap.addValue("offset", pageable.getPageSize());

		StringBuffer articleIndexQuery = new StringBuffer("""
			SELECT
			        A.ARTICLE_ID   ,
			        A.TITLE        ,
			        A.CONTENT      ,
			        A.CREATED_AT   ,
			        A.CREATED_BY   ,
			        A.VIEW_COUNT   ,
			        A.LIKE_COUNT   ,
			        A.COMMENT_COUNT,
			        I.SORT_NUMBER  ,
			        I.URL          ,
			        CNT_I.CNT
			FROM (
				SELECT
						*
				FROM ARTICLE
				WHERE DELETED_AT IS NULL
				AND BOARD_TYPE = :boardType 
			""");

		if (!ObjectUtils.isEmpty(cursor)) {
			articleIndexQuery.append(" AND ARTICLE_ID < :articleId ");
			paramMap.addValue("articleId", cursor);
		}

		if (!ObjectUtils.isEmpty(userId)) {
			articleIndexQuery.append(" AND CREATED_BY = :userId ");
			paramMap.addValue("userId", userId);
		}

		if (pageable.getSort().stream().anyMatch(order -> order.getProperty().equals("comment"))) {
			articleIndexQuery.append(" AND CREATED_AT >  current_date - 7 ");
		}

		articleIndexQuery
			.append(" ORDER BY ")
			.append(articleSort(pageable))
			.append(" DESC LIMIT :offset ) A ");
		articleIndexQuery.append(onlyPicture ? " INNER " : " LEFT ");

		articleIndexQuery.append("""
			JOIN IMAGE I
			ON A.ARTICLE_ID  = I.ARTICLE_ID
			AND I.SORT_NUMBER = 1
			LEFT JOIN (
			SELECT
					ARTICLE_ID,
					COUNT(ARTICLE_ID) AS CNT
			FROM IMAGE
			GROUP BY ARTICLE_ID 
			) CNT_I
			ON I.ARTICLE_ID = CNT_I.ARTICLE_ID 
			""");
		if (onlyPicture) {
			articleIndexQuery
				.append(" ORDER BY ")
				.append(articleSort(pageable))
				.append(" DESC ");
		}

		List<ArticleBriefResponse> articleJpaEntityList = jdbcClient
			.sql(articleIndexQuery.toString())
			.paramSource(paramMap)
			.query((rs, rowNum) -> ArticleBriefResponse.builder()
				.articleId(rs.getLong("article_id"))
				.title(rs.getString("title"))
				.content(rs.getString("content"))
				.createdAt(
					rs.getTimestamp("created_at") == null ? null : rs.getTimestamp("created_at").toLocalDateTime())
				.createdBy(rs.getLong("created_by"))
				.viewCount(rs.getInt("view_count"))
				.likeCount(rs.getInt("like_count"))
				.commentCount(rs.getInt("comment_count"))
				.sortNumber(rs.getInt("sort_number"))
				.url(rs.getString("url"))
				.imageTotalCount(rs.getInt("cnt"))
				.build()
			)
			.list();

		//TODO: JdbcClient로 대체하여 Page 수정 필요

		JPAQuery<Long> totalCount = jpaQueryFactory
			.select(articleJpaEntity.count())
			.from(articleJpaEntity)
			.where(
				boardTypeEq(boardType),
				articleJpaEntity.deletedAt.isNull()
			);
		if (onlyPicture) {
			totalCount
				.innerJoin(imageJpaEntity)
				.on(articleJpaEntity.articleId.eq(imageJpaEntity.articleId));
		}

		return PageableExecutionUtils.getPage(articleJpaEntityList, pageable, totalCount::fetchOne);

	}

	private String articleSort(Pageable pageable) {

		if (pageable.getSort().isEmpty()) {
			return " created_at ";
		}

		for (Sort.Order order : pageable.getSort()) {
			return switch (order.getProperty()) {
				case "liked" -> "like_count DESC, article_id ";
				case "comment" -> "comment_count DESC, created_at ";
				case "hits" -> "view_count DESC, created_at ";
				default -> " created_at ";
			};
		}

		return "created_at";
	}

	@Override
	public Long updateArticle(Article changedArticle) {
		ArticleJpaEntity foundArticle = jpaQueryFactory
			.selectFrom(articleJpaEntity)
			.where(
				boardTypeEq(changedArticle.getBoardType()),
				articleIdEq(changedArticle.getId())
			).fetchOne();

		if (foundArticle == null) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "해당하는 게시글이 없습니다.");
		}

		foundArticle.changeArticleContents(changedArticle.getTitle(), changedArticle.getContent(),
			changedArticle.isEnableComment());

		return foundArticle.getArticleId();
	}

	@Override
	public int increaseViewCount(BoardType boardType, Long articleId) {
		return articleRepository.increaseViewCount(boardType.name(), articleId);
	}

	@Override
	public int increaseLikeCount(BoardType boardType, Long articleId) {
		return articleRepository.increaseLikeCount(boardType.name(), articleId);
	}

	@Override
	public int decreaseLikeCount(BoardType boardType, Long articleId) {
		return articleRepository.decreaseLikeCount(boardType.name(), articleId);
	}

	@Override
	public int increaseCommentCount(BoardType boardType, Long articleId) {
		return articleRepository.increaseCommentCount(boardType.name(), articleId);
	}

	@Override
	public int decreaseCommentCount(BoardType boardType, Long articleId) {
		return articleRepository.decreaseCommentCount(boardType.name(), articleId);
	}

	@Override
	public Long markDeleted(ArticleRemoveCommand articleRemoveCommand) {

		ArticleJpaEntity articleJpa = articleRepository.findById(articleRemoveCommand.getArticleId())
			.orElseThrow(() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST));
		articleJpa.markDeletedNow();
		return 1L;

	}

	private BooleanExpression boardTypeEq(BoardType boardType) {
		return articleJpaEntity.boardType.eq(boardType);
	}

	private BooleanExpression articleIdEq(Long articleId) {
		return articleJpaEntity.articleId.eq(articleId);
	}
*/

}
