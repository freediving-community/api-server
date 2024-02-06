package com.freediving.communityservice.adapter.out.persistence.constant;

import com.freediving.communityservice.adapter.in.web.UserProvider;

import lombok.Getter;

@Getter
public enum BoardType {
	GENERAL(UserProvider.RoleLevel.USER.getValue()),
	BUDDY_QNA(UserProvider.RoleLevel.USER.getValue()),
	BUDDY_CHAT(5),
	QNA(5),
	TIP(5);

	private final int roleLevel;

	BoardType(int roleLevel) {
		this.roleLevel = roleLevel;
	}

}
