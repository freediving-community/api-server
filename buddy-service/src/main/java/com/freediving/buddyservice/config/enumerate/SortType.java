package com.freediving.buddyservice.config.enumerate;

public enum SortType {
	POPULARITY("인기순"),
	DEADLINE("마감 임박순"),
	NEWEST("최신순");

	private final String description;

	SortType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}