package com.freediving.buddyservice.domain.query.component.common;

import com.freediving.common.enumerate.DivingPool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DivingPoolInfoResponse {
	private DivingPool divingPoolId;
	private String divingPoolName;
}