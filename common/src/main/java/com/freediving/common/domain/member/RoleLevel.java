package com.freediving.common.domain.member;

import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/24
 * @Description    : 사용자 ROLE 정보 관리를 위한 ENUM
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/24        sasca37       최초 생성
 */

@Getter
public enum RoleLevel {

	ANONYMOUS(-1),            // 비로그인 사용자

	UNREGISTER(5),            // 라이센스 미등록 사용자
	WAIT_LICENSE_APPROVAL(7), // 심사중
	NO_LEVEL(9),              // 레벨 0
	LEVEL1(10),
	LEVEL2(20),
	LEVEL3(30),
	LEVEL4(40),
	INSTRUCTOR(50),
	ADMIN(99);

	private final int level;

	RoleLevel(int level) {
		this.level = level;
	}
	public static final RoleLevel[] LEVEL_ARR = {RoleLevel.NO_LEVEL, RoleLevel.LEVEL1, RoleLevel.LEVEL2, RoleLevel.LEVEL3, RoleLevel.LEVEL4, RoleLevel.INSTRUCTOR};
}
