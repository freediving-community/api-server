package com.freediving.buddyservice.domain.query.component.common;

import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "다이빙 풀 정보 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DivingPoolInfoResponse {

	@Schema(description = "다이빙 풀 ID", example = "PARADIVE")
	private DivingPool divingPoolId;

	@Schema(description = "다이빙 풀 이름", example = "파라다이브")
	private String divingPoolName;
}