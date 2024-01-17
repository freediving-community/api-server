package com.freediving.memberservice.domain;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : Oauth 정보를 관리 도메인 (Google, Kakao, Naver)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */

@Getter
public enum OauthType {
	GOOGLE("GOOGLE"),
	NAVER("NAVER"),
	KAKAO("KAKAO");

	private final String name;

	OauthType(String name) {
		this.name = name;
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/15
	 * @Param            : OauthType 이름 (대/소문자 허용)
	 * @Return           : OauthType
	 * @Description      : JsonCreator DELEGATING를 사용하여 파라미터에 맞지 않는 값을 허용하고, EnumValid 어노테이션으로 검증
	 */
	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static OauthType from(String type) {
		return Stream.of(OauthType.values())
			.filter(v -> StringUtils.equals(v.getName(), type.toUpperCase()))
			.findFirst()
			.orElse(null);
	}
}
