package com.freediving.divingpool.data.dto;

import com.freediving.common.enumerate.DivingPool;
import com.freediving.divingpool.data.dao.DivingPoolJpaEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class DivingPoolSimpleResponse {

	private DivingPool divingPoolId;
	private String divingPoolName;
	private String address;
	private String description;

	@Override
	public String toString() {
		return "DivingPoolResponse{" +
			"divingPoolId='" + divingPoolId.toString() + '\'' +
			", divingPoolName=" + divingPoolName +
			", address='" + address + '\'' +
			", description='" + description +
			'}';
	}

	public static DivingPoolSimpleResponse of(DivingPoolJpaEntity entity) {
		return DivingPoolSimpleResponse.builder()
			.divingPoolId(entity.getDivingPoolId())
			.divingPoolName(entity.getDivingPoolName())
			.address(entity.getAddress())
			.description(entity.getDescription())
			.build();
	}
}
