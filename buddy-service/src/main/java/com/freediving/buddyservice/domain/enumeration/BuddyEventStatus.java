package com.freediving.buddyservice.domain.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(name = "버디 이벤트 상태 ( Enum )", description = "버디 이벤트 상태 ( Enum )", example = "RECRUITING")
public enum BuddyEventStatus {

	@Schema(description = "모집 중")
	RECRUITING("모집 중"),

	@Schema(description = "모집 마감")
	RECRUITMENT_CLOSED("모집 마감"),

	@Schema(description = "모집 삭제")
	RECRUITMENT_DELETED("모집 삭제");

	private final String displayValue;
}
