package com.freediving.buddyservice.adapter.out.persistence.event.querydsl;

import com.freediving.common.enumerate.DivingPool;
import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BuddyEventDivingPoolMappingProjectDto {
	private Long eventId;
	private DivingPool divingPoolId;
	private String divingPoolName;

	@QueryProjection
	public BuddyEventDivingPoolMappingProjectDto(Long eventId, DivingPool divingPoolId, String divingPoolName) {
		this.eventId = eventId;
		this.divingPoolId = divingPoolId;
		this.divingPoolName = divingPoolName;
	}
}
