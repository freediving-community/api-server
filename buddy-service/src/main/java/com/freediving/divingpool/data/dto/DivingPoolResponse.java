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
public class DivingPoolResponse {

	private DivingPool divingPoolId;
	private String divingPoolName;
	private String address;
	private String description;
	private Integer displayOrder;

	@Override
	public String toString() {
		return "DivingPoolResponse{" +
			"divingPoolId='" + divingPoolId.toString() + '\'' +
			", divingPoolName=" + divingPoolName +
			", address='" + address + '\'' +
			", description='" + description + '\'' +
			", displayOrder=" + displayOrder +
			'}';
	}

	public static DivingPoolResponse of(DivingPoolJpaEntity entity) {
		return DivingPoolResponse.builder()
			.divingPoolId(entity.getDivingPoolId())
			.divingPoolName(entity.getDivingPoolName())
			.address(entity.getAddress())
			.description(entity.getDescription())
			.displayOrder(entity.getDisplayOrder())
			.build();
	}
}
