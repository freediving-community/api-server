package com.freediving.communityservice.adapter.out.persistence.constant;

import lombok.Getter;

@Getter
public enum MsgType {

	CHAT("chat"),
	NOTICE("notice"),
	SYSTEM("system"),
	IMAGE("image");

	private final String type;

	MsgType(String type) {
		this.type = type.toLowerCase();
	}

}
