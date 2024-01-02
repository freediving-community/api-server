package com.freediving.authservice.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum OauthType {
	GOOGLE("GOOGLE"),
	NAVER("NAVER"),
	KAKAO("KAKAO");

	private final String name;

	OauthType(String name) {
		this.name = name;
	}

	public static OauthType from(String type) {
		return Arrays.stream(OauthType.values())
			.filter(value -> value.getName().equals(type.toUpperCase()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 로그인 타입입니다."));
	}
}
