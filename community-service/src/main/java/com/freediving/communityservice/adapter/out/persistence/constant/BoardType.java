package com.freediving.communityservice.adapter.out.persistence.constant;

import com.freediving.communityservice.adapter.in.web.UserProvider;

import lombok.Getter;

@Getter
public enum BoardType {
	GENERAL(UserProvider.RoleLevel.UNREGISTER.getValue()),
	DIVING_POOL(UserProvider.RoleLevel.UNREGISTER.getValue()),
	BUDDY_QNA(UserProvider.RoleLevel.UNREGISTER.getValue()),
	BUDDY_CHAT(UserProvider.RoleLevel.UNREGISTER.getValue()),
	QNA(UserProvider.RoleLevel.UNREGISTER.getValue()),
	TIP(UserProvider.RoleLevel.LEVEL0.getValue()),
	REPORT(UserProvider.RoleLevel.UNREGISTER.getValue());

	private final int createRoleLevel;

	BoardType(int createRoleLevel) {
		this.createRoleLevel = createRoleLevel;
	}

}
