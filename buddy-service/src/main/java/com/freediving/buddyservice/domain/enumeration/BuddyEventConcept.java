package com.freediving.buddyservice.domain.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(name = "버디 이벤트 컨셉 ( Enum )", description = "버디 이벤트 컨셉 ( Enum )", example = "FUN")
public enum BuddyEventConcept {
	//TODO enum 재 설정 필요
	@Schema(description = "펀다이빙")
	FUN("펀다이빙"),

	@Schema(description = "연습")
	PRACTICE("연습"),

	@Schema(description = "사진촬영")
	PHOTO("사진촬영"),

	@Schema(description = "머메이드")
	MERMAID("머메이드"),

	@Schema(description = "강습")
	TRAINING("강습");

	private final String displayValue;

}
