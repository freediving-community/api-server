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
@Schema(name = "다이빙 풀 조회하기 ( 간단한 정보 응답 )", description = "다이빙 풀 간단한 정보 응답")
public class DivingPoolSimpleResponse {

	@Schema(description = "다이빙 풀 ID", example = "DEEPSTATION", implementation = DivingPool.class)
	private DivingPool divingPoolId;

	@Schema(description = "다이빙 풀 이름", example = "딥스테이션")
	private String divingPoolName;

	@Schema(description = "주소", example = "경기 용인시 처인구 포곡읍 성산로 523 딥스테이션")
	private String address;

	@Schema(description = "설명", example = "딥스테이션은 예약제로 운영되며, 2레벨 이상 이용 가능. 평일에는 다양한 입장권이 제공됩니다.")
	private String description;

	public static DivingPoolSimpleResponse of(DivingPoolJpaEntity entity) {
		return DivingPoolSimpleResponse.builder()
			.divingPoolId(entity.getDivingPoolId())
			.divingPoolName(entity.getDivingPoolName())
			.address(entity.getAddress())
			.description(entity.getDescription())
			.build();
	}

	@Override
	public String toString() {
		return "DivingPoolResponse{" +
			"divingPoolId='" + divingPoolId.toString() + '\'' +
			", divingPoolName=" + divingPoolName +
			", address='" + address + '\'' +
			", description='" + description +
			'}';
	}
}
