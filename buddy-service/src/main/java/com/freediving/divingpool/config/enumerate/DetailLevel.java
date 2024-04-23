package com.freediving.divingpool.config.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DetailLevel {
	LOW, HIGH;

	@JsonCreator // Jackson 라이브러리가 JSON으로부터 객체를 생성할 때 사용
	public static DetailLevel fromString(String value) {
		for (DetailLevel level : DetailLevel.values()) {
			if (level.name().equalsIgnoreCase(value)) {
				return level;
			}
		}
		throw new IllegalArgumentException("Invalid detail level: " + value);
	}
}
