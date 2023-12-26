package com.freediving.buddyservice.common.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventStatus {
	//TODO enum 재 설정 필요
	RECRUITMENT_CREATED("모집 생성"),
	RECRUITING("모집 중"),
	RECRUITMENT_CLOSED("모집 마감"),
	RECRUITMENT_DELETED("모집 삭제");

	private final String displayValue;

}
