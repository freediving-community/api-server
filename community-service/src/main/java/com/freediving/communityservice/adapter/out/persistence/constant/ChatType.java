package com.freediving.communityservice.adapter.out.persistence.constant;

import lombok.Getter;

@Getter
public enum ChatType {
	BUDDY("BUDDY"),
	DIRECT("DIRECT"),
	GROUP("GROUP");

	private final String type;

	ChatType(String type) {
		this.type = type.toUpperCase();
	}

}
