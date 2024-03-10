package com.freediving.divingpool.data.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class DivingPoolListResponse {

	private List<DivingPoolResponse> divingPools;

	@Override
	public String toString() {
		return "DivingPoolListResponse{" +
			"divingPools=" + divingPools +
			'}';
	}
}
