package com.freediving.buddyservice.common.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FreedivingLevel {

	//TODO enum 재 설정 필요
	ADVANCED_ONLY("3~ 상급자끼리 연습을 원함"),
	BEGINNER_ONLY("1~2 초보끼리만 놀고 싶음"),
	INTERMEDIATE_UPWARD("2~ 초보말고, 2는 됐으면 함"),
	OPEN_TO_ALL("1~ 초보부터, 누구나");

	private final String displayValue;

}
