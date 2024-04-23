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
	private String simpleAddress; // 새로 추가된 필드
	private String operatingHours;
	private String priceInfo;
	private String websiteUrl;
	private String recommendedLevel;
	private String depth;
	private String contactNumber;
	private String regularClosure;

	public static DivingPoolResponse of(DivingPoolJpaEntity entity) {
		return DivingPoolResponse.builder()
			.divingPoolId(entity.getDivingPoolId())
			.divingPoolName(entity.getDivingPoolName())
			.address(entity.getAddress())
			.simpleAddress(entity.getSimpleAddress())
			.operatingHours(entity.getOperatingHours())
			.priceInfo(entity.getPriceInfo())
			.websiteUrl(entity.getWebsiteUrl())
			.recommendedLevel(entity.getRecommendedLevel())
			.depth(entity.getDepth())
			.contactNumber(entity.getContactNumber())
			.regularClosure(entity.getRegularClosure())
			.description(entity.getDescription())
			.build();
	}
}
