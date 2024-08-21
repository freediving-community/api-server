package com.freediving.communityservice.adapter.out.persistence.chat;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Table(name = "chat_msg")
@Entity
public class ChatMsgJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long msgId;

	@Column(nullable = false)
	private Long chatRoomId;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String msg;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MsgType msgType;

	@Column
	private Long replyToMsgId;

	@Column(nullable = true)
	private LocalDateTime deletedAt;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false, updatable = false)
	private Long createdBy;

	@Builder
	public ChatMsgJpaEntity(Long msgId, Long chatRoomId, String msg, MsgType msgType, Long replyToMsgId,
		LocalDateTime deletedAt, LocalDateTime createdAt, Long createdBy) {
		this.msgId = msgId;
		this.chatRoomId = chatRoomId;
		this.msg = msg;
		this.msgType = msgType;
		this.replyToMsgId = replyToMsgId;
		this.deletedAt = deletedAt;
		this.createdAt = LocalDateTime.now();
		this.createdBy = createdBy;
	}
}
