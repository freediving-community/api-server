package com.freediving.buddyservice.adapter.in.kafka.dto;

import java.util.Set;

import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "topic/buddyservice.task.user-pool-preferences 메시지")
public class PoolPreference {
	@Schema(description = "사용자의 고유 ID", example = "12345")
	private Long userId;

	@Schema(description = "선호하는 다이빙 풀들의 ID 집합 \n 최대 2개입니다.   \n - TODO 예외 전파 ", example = "[\"DEEPSTATION\", \"PARADIVE\"]", implementation = DivingPool.class)
	private Set<String> preferredPools;
}