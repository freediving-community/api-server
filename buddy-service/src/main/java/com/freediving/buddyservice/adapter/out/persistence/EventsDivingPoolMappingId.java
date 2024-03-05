package com.freediving.buddyservice.adapter.out.persistence;

import java.io.Serializable;

import com.freediving.common.enumerate.DivingPool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventsDivingPoolMappingId implements Serializable {
	private DivingPool divingPoolId; // 복합 키의 일부
	private Long eventId; // 복합 키이면서 외래 키
}
