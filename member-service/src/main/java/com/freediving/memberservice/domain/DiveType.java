package com.freediving.memberservice.domain;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/23
 * @Description    : 다이빙 타입 정보 (스쿠버, 프리다이빙)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/23        sasca37       최초 생성
 */

@Getter
public enum DiveType {
	FREE_DIVE("F"),
	SCUBA_DIVE("S");
	private final String code;

	DiveType(String code) {
		this.code = code;
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/15
	 * @Param            : DiveType 이름 (대/소문자 허용)
	 * @Return           : DiveType
	 * @Description      : JsonCreator DELEGATING를 사용하여 파라미터에 맞지 않는 값을 허용하고, EnumValid 어노테이션으로 검증
	 */
	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static DiveType from(String type) {
		return Stream.of(DiveType.values())
			.filter(v -> StringUtils.equals(v.getCode(), type.toUpperCase()))
			.findFirst()
			.orElse(null);
	}
}
