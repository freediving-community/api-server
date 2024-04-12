package com.freediving.memberservice.domain;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/31
 * @Description    : 라이센스 상태값 정보 (활성화, 중지, 탈퇴)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/31        sasca37       최초 생성
 */

@Getter
public enum LicenseStatus {

	EVALUATION("0"),
	APPROVED("1"),

	REJECTED("2");

	private final String code;

	LicenseStatus(String code) {
		this.code = code;
	}

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static LicenseStatus from(String type) {
		return Stream.of(LicenseStatus.values())
			.filter(v -> StringUtils.equals(v.getCode(), type.toUpperCase()))
			.findFirst()
			.orElse(null);
	}
}
