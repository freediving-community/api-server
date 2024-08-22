package com.freediving.communityservice.adapter.out.persistence.chat;

import static com.freediving.communityservice.adapter.out.persistence.chat.QChatRoomJpaEntity.*;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomListResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomEditPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.domain.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ChatRoomReadPort, ChatRoomCreationPort, ChatRoomEditPort {

	private final ChatRoomRepository chatRoomRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final ChatRoomPersistenceMapper chatRoomMapper;
	private final JdbcClient jdbcClient;

	@Override
	public ChatRoom getChatRoom(ChatType chatType, Long targetId) {
		Optional<ChatRoomJpaEntity> chatRoom = getChatRoomByChatTypeAndTargetId(chatType, targetId);
		return chatRoom.map(chatRoomMapper::mapToDomain).orElse(null);
	}

	@Override
	public List<ChatRoomListResponse> getChatRoomList(Long userId, ChatType chatType) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("userId", userId);

		StringBuffer sql = new StringBuffer("""
			SELECT CR.CHAT_ROOM_ID
				,CR.CHAT_TYPE
				,CR.CREATED_AT
				,CR.CREATED_BY
				,CR.DELETED_AT
				,CR.ENABLED
				,CR.MODIFIED_AT
				,CR.MODIFIED_BY
				,CR.OPEN_CHAT_ROOMURL
				,CR.PARTICIPANT_COUNT
				,CR.TARGET_ID
				,CR.TITLE
				,(CRU.LAST_CHECKED_MSG_ID != CM.MSG_ID ) AS HAS_NEW
			FROM CHAT_ROOM CR 
			INNER JOIN CHAT_ROOM_USER CRU 
				ON CR.DELETED_AT IS NULL
				AND CR.CHAT_ROOM_ID = CRU.CHAT_ROOM_ID 
				AND CRU.USER_ID = :userId 
			""");
		if (!ObjectUtils.isEmpty(chatType)) {
			sql.append("""
					AND CR.CHAT_TYPE = :chatType
				""");
			paramMap.addValue("chatType", chatType.name());
		}
		sql.append("""
				 LEFT JOIN ( 
			  	SELECT CHAT_ROOM_ID , MAX(MSG_ID) AS MSG_ID 
			  	FROM CHAT_MSG 
			  	WHERE DELETED_AT IS NULL 
			  	GROUP BY CHAT_ROOM_ID 
			  ) CM 
			  ON CRU.CHAT_ROOM_ID = CM.CHAT_ROOM_ID 
			""");
		;

		return jdbcClient.sql(sql.toString())
			.paramSource(paramMap)
			.query(
				(rs, rowNum) -> ChatRoomListResponse.builder()
					.chatRoomId(rs.getLong("chat_room_id"))
					.chatType(ChatType.valueOf(rs.getString("chat_type")))
					.title(rs.getString("title"))
					.participantCount(rs.getLong("participant_count"))
					.enabled(rs.getBoolean("enabled"))
					.createdBy(rs.getLong("created_by"))
					.hasNew(rs.getBoolean("has_new"))
					.build()
			).list();
	}

	@Override
	public ChatRoom createChatRoom(Long requestUserId, ChatType chatType, Long buddyEventId, String title,
		Long participantCount,
		String openChatRoomURL) {
		ChatRoomJpaEntity createdChatRoom = chatRoomRepository.save(
			ChatRoomJpaEntity.of(requestUserId, chatType, buddyEventId, title, participantCount, openChatRoomURL)
		);
		return chatRoomMapper.mapToDomain(createdChatRoom);
	}

	@Override
	public ChatRoom editChatRoom(Long requestUserId, ChatType chatType, Long targetId, String title,
		Long participantCount, String openChatRoomURL) {

		Optional<ChatRoomJpaEntity> chatRoom = getChatRoomByChatTypeAndTargetId(chatType, targetId);
		ChatRoomJpaEntity foundChatRoom = chatRoom.orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST));

		foundChatRoom.editChatRoomInfo(title, participantCount, openChatRoomURL);
		chatRoomRepository.save(foundChatRoom);

		return chatRoomMapper.mapToDomain(foundChatRoom);
	}

	private Optional<ChatRoomJpaEntity> getChatRoomByChatTypeAndTargetId(ChatType chatType, Long targetId) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(chatRoomJpaEntity)
			.where(
				chatRoomJpaEntity.chatType.eq(chatType),
				chatRoomJpaEntity.targetId.eq(targetId),
				chatRoomJpaEntity.deletedAt.isNull()
			).fetchOne());
	}

}
