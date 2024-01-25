package com.freediving.memberservice.domain;

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

	ANONYMOUS(-1),    // 비로그인 사용자

	UNREGISTER(5),    // 라이센스 미등록 사용자

	LEVEL0(10),
	LEVEL1(15),
	LEVEL2(20),
	LEVEL3(25),
	LEVEL4(30),
	INSTRUCTOR(35),

	ADMIN(99);

	private final int code;

	RoleLevel(int code) {
		this.code = code;
	}
}
