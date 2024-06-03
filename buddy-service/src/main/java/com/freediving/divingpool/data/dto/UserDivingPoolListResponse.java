package com.freediving.divingpool.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자가 선호하는 다이빙 풀의 ID만 응답하기 위한 DTO
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-03-21
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(title = "사용자 선호 다이빙 풀 조회 결과", description = "GET /v1/internal/pool 사용자 선호 다이빙 풀 조회 결과", hidden = true)
public class UserDivingPoolListResponse {
	private List<DivingPool> divingPools;

	public void add(DivingPool divingPool) {
		if (divingPools == null)
			this.divingPools = new ArrayList<>();

		this.divingPools.add(divingPool);
	}

}
