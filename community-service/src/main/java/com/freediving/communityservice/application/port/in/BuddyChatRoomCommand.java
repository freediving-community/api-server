package com.freediving.communityservice.application.port.in;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class BuddyChatRoomCommand extends SelfValidating<BuddyChatRoomCommand> {

	@NotNull
	private Long eventId;

	@NotNull
	private Long createdBy;

	private LocalDateTime eventStartDate;

	private List<Long> participants;

	private String status;

	private List<String> divingPools;

	@NotNull
	private final ChatType chatType;

	public BuddyChatRoomCommand(Long eventId, Long createdBy, LocalDateTime eventStartDate, List<Long> participants,
		String status, List<String> divingPools, ChatType chatType) {
		this.eventId = eventId;
		this.createdBy = createdBy;
		this.eventStartDate = eventStartDate;
		this.participants = participants;
		this.status = status;
		this.divingPools = divingPools;
		this.chatType = chatType;
		this.validateSelf();
	}
}
