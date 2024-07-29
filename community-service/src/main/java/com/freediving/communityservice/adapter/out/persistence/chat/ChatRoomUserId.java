package com.freediving.communityservice.adapter.out.persistence.chat;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Embeddable
public class ChatRoomUserId implements Serializable {
	private Long userId;
	private Long chatRoomId;
}
