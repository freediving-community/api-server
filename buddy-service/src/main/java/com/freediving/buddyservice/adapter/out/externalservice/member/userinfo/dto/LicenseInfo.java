package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "유저 라이센스 정보", description = "유저 라이센스")
public class LicenseInfo {
	@Schema(description = "유저 프리다이빙 라이센스")
	private FreeDiving freeDiving;
	@Schema(description = "유저 스쿠버다이빙 라이센스")
	private ScubaDiving scubaDiving;
}
