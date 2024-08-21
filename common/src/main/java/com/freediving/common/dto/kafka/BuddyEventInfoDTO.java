package com.freediving.common.dto.kafka;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "버디 매칭 참여 정보")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Data
public class BuddyEventInfoDTO {

	@Schema(description = "버디 이벤트 ID", example = "1")
	private Long eventId;

	@Schema(description = "모집장 ID", example = "1")
	private Long createdBy;

	@Schema(description = "이벤트 시작 날짜", example = "2024-08-16T09:00:00")
	private LocalDateTime eventStartDate;

	@Schema(description = "참가 승인자 ID", example = "[2,3,4,5]")
	private List<Long> participants;
	/*
	*  BuddyEventStatus
	* 	@Schema(description = "모집 중")
		RECRUITING("모집 중"),

		@Schema(description = "모집 마감")
		RECRUITMENT_CLOSED("모집 마감"),

		@Schema(description = "모집 삭제")
		RECRUITMENT_DELETED("모집 삭제");
	* */
	@Schema(description = "이벤트 상태")
	private String status;

	@Schema(description = "모집 희망 다이빙풀")
	private List<String> divingPools;

	@Schema(description = "오픈채팅방 URL")
	private String openChatRoomUrl;
}
