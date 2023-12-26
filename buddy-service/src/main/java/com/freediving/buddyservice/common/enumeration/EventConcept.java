package com.freediving.buddyservice.common.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventConcept {
	//TODO enum 재 설정 필요
	FUN_DIVING("펀다이빙"),
	PRACTICE("연습"),
	PHOTOGRAPHY("사진촬영"),
	TRAINING("강습"),
	LEVEL_UP("레벨업");

	private final String displayValue;

}
