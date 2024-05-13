package com.freediving.buddyservice.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BuddyEventConcept {
	//TODO enum 재 설정 필요
	FUN("펀다이빙"),
	PRACTICE("연습"),
	PHOTO("사진촬영"),
	MERMAID("이드"),
	TRAINING("강습");

	private final String displayValue;

}
