package com.freediving.communityservice.adapter.out.persistence.chat;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // null 인 값은 제외하고 Insert. DB DefaultValue 사용을 위함.
// @SQLDelete(sql = "UPDATE article SET deleted_at = SYSDATE WHERE article_id = ?")
@Table(name = "chat_room")
@Entity
public class ChatRoomJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private ChatType chatType;

	@Column(nullable = true)
	private Long targetId;

	@Column(nullable = false, length = 500)
	private String title;

	@Column(nullable = false)
	private Long participantCount;

	@Column(nullable = true)
	private String openChatRoomURL;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean enabled;

	@Column(nullable = true)
	private LocalDateTime deletedAt;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false, updatable = false)
	private Long createdBy;

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime modifiedAt;

	@Column
	private Long modifiedBy;

	public static ChatRoomJpaEntity of(Long requestUserId, ChatType chatType, Long targetId, String title,
		Long participantCount, String openChatRoomURL) {
		return ChatRoomJpaEntity.builder()
			.createdBy(requestUserId)
			.chatType(chatType)
			.targetId(targetId)
			.title(title)
			.participantCount(participantCount)
			.openChatRoomURL(openChatRoomURL)
			.enabled(true)
			.createdAt(LocalDateTime.now())
			.build();
	}

	public void editChatRoomInfo(String title, Long participantCount, String openChatRoomURL) {
		this.title = title;
		this.participantCount = participantCount;
		this.openChatRoomURL = openChatRoomURL;
	}
}




