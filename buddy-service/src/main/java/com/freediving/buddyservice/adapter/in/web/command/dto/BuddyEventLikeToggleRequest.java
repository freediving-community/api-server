package com.freediving.buddyservice.adapter.in.web.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* 버디이벤트의 좋아요 설정, 해지의 POST 요청을 받는 DTO객체
 * 플래그 값을 가지고 있으며 플래그에 따라 설정, 해지를 판별한다.
*  likeStatus가 True일 경우 설정, False일 경우 해지를 나타낸다.
* @author pus__
* @version 1.0.0
* 작성일 2024-04-10
**/

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "버디 이벤트 좋아요 설정/해지 요청", name = "BuddyEventLikeToggleRequest", description = "POST /v1/event/like 버디 이벤트 좋아요 설정/해지 요청에 사용 되는 Body 스키마")
public class BuddyEventLikeToggleRequest {

	@Schema(description = "버디 이벤트 식별 ID", type = "string", example = "314123", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "버디 이벤트 식별 ID는 필수입니다.")
	private Long eventId;

	@Schema(description = "좋아요 설정/해지 플래그", type = "string", example = "TRUE", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "likeStatus는 필수입니다.")
	private boolean likeStatus;

}
