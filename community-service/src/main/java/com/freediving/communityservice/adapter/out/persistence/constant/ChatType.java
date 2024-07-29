package com.freediving.communityservice.adapter.out.persistence.constant;

import lombok.Getter;

@Getter
public enum ChatType {
	BUDDY("buddy"),
	DIRECT("direct"),
	GROUP("group");

	private final String type;

	ChatType(String type) {
		this.type = type.toLowerCase();
	}

}
