package com.freediving.communityservice.adapter.out.persistence.chat;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chat_room_user")
@Entity
public class ChatRoomUserJpaEntity {
	// @Id
	// @ManyToOne
	// @JoinColumn(name = "user_id", nullable = false)
	// private Long userId; // 사용자를 참조하는 엔티티
	//
	// @Id
	// @ManyToOne
	// @JoinColumn(name = "chat_room_id", nullable = false)
	// private Long chatRoomId; // 채팅방을 참조하는 엔티티
	@EmbeddedId
	private ChatRoomUserId id; // 복합 키
	// Auditing
	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
}




