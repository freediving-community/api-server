package com.freediving.buddyservice.domain.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "참여 상태 ( Enum )", description = "참여 상태 ( Enum )", example = "OWNER")
public enum ParticipationStatus {
	@Schema(description = "이벤트 생성자")
	OWNER,

	@Schema(description = "신청자")
	APPLIED,

	@Schema(description = "참여자")
	PARTICIPATING,

	@Schema(description = "거절됨")
	REJECTED
}
