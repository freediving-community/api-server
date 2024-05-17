package com.freediving.divingpool.data.dto;

import com.freediving.common.enumerate.DivingPool;
import com.freediving.divingpool.data.dao.DivingPoolJpaEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Schema(name = "다이빙 풀 조회하기 ( 상세 정보 응답 )", description = "다이빙 풀 상세 정보 응답")
public class DivingPoolResponse {

	@Schema(description = "다이빙 풀 ID", example = "DEEPSTATION", implementation = DivingPool.class)
	private DivingPool divingPoolId;

	@Schema(description = "다이빙 풀 이름", example = "딥스테이션")
	private String divingPoolName;

	@Schema(description = "주소", example = "경기 용인시 처인구 포곡읍 성산로 523 딥스테이션")
	private String address;

	@Schema(description = "설명", example = "딥스테이션은 예약제로 운영되며, 2레벨 이상 이용 가능. 평일에는 다양한 입장권이 제공됩니다.")
	private String description;

	@Schema(description = "간단한 주소", example = "경기 용인시 처인구 포곡읍 성산로 523")
	private String simpleAddress;

	@Schema(description = "운영 시간", example = "08:00 - 23:00 (3시간 간격)")
	private String operatingHours;

	@Schema(description = "가격 정보", example = "평일: 44,000원\n휴일: 66,000원")
	private String priceInfo;

	@Schema(description = "웹사이트 URL", example = "https://deepstation.kr/")
	private String websiteUrl;

	@Schema(description = "추천 레벨", example = "2")
	private String recommendedLevel;

	@Schema(description = "수심", example = "36m")
	private String depth;

	@Schema(description = "연락처", example = "032-880-9768")
	private String contactNumber;

	@Schema(description = "정기 휴무", example = "매주 월요일")
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
