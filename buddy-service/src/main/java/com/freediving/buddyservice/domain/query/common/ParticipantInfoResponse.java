package com.freediving.buddyservice.domain.query.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "참가자 정보 응답", description = "버디 조회하기의 참가자 정보 응답")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ParticipantInfoResponse {

	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "프로필 이미지 URL", example = "http://example.com/profile.jpg")
	private String profileImgUrl;
}
