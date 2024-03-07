package com.freediving.buddyservice.common.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FreedivingLevel {

	//TODO enum 재 설정 필요
	LEVEL0("레벨 0", "아직 자격증이 없어요"),
	LEVEL1("레벨 1", "LAP 1, AIDA Lv.1, 패디 베이직"),
	LEVEL2("레벨 2", "WAVE 1, AIDA Lv.2, 패디 프리다이버"),
	LEVEL3("레벨 3", "WAVE 2, AIDA Lv.3, 패디 어드밴스드"),
	LEVEL4("레벨 4", "WAVE 3, AIDA Lv.4, 패디 마스터");

	private final String displayTitle;
	private final String displayDesc;

}
