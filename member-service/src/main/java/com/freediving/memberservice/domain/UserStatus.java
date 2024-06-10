package com.freediving.memberservice.domain;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/31
 * @Description    : 유저 상태값 정보 (활성화, 중지, 탈퇴)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/31        sasca37       최초 생성
 */

@Getter
public enum UserStatus {
	ACTIVE("정상 사용자"),

	SUSPENDED("정지된 사용자"),

	WITHDRAWN("탈퇴한 사용자"),

	UNKNOWN("존재하지 않는 사용자")
	;

	private final String code;

	UserStatus(String code) {
		this.code = code;
	}

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static UserStatus from(String type) {
		return Stream.of(UserStatus.values())
			.filter(v -> StringUtils.equals(v.getCode(), type.toUpperCase()))
			.findFirst()
			.orElse(null);
	}
}
