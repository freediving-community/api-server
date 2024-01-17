package com.freediving.memberservice.domain;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/16
 * @Description    : 성별 정보를 저장하는 도메인 ( 남 (0) / 여 (1) )
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/16        sasca37       최초 생성
 */
@Getter
public enum SexType {

	MALE("0"),
	FEMALE("1");

	private final String code;

	SexType(String code) {
		this.code = code;
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/16
	 * @Param            : 남/여 코드 (0 또는 1)
	 * @Return           : 코드에 매핑되는 경우 SexType, 없는 경우 null
	 * @Description      : 코드값을 입력받아 성별 정보를 반환 (JsonCreator DELEGATING를 사용하여 파라미터에 맞지 않는 값을 허용하고, EnumValid 어노테이션으로 검증)
	 */
	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static SexType from(String code) {
		return Stream.of(SexType.values())
			.filter(v -> StringUtils.equals(v.getCode(), code.toUpperCase()))
			.findFirst()
			.orElse(null);
	}
}
