package com.freediving.communityservice.adapter.in.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProvider {

	private Long requestUserId;
	private RoleLevel roleLevel;

	public UserProvider(Long requestUserId, RoleLevel roleLevel) {
		this.requestUserId = requestUserId;
		this.roleLevel = roleLevel != null ? roleLevel : RoleLevel.ANONYMOUS;
	}

	@Getter
	public enum RoleLevel {
		ANONYMOUS(0),
		USER(1),
		ADMIN(99);

		private final int value;

		RoleLevel(int value) {
			this.value = value;
		}

		public static RoleLevel valueOf(int value) {
			for (RoleLevel role : RoleLevel.values()) {
				if (role.getValue() == value) {
					return role;
				}
			}
			throw new IllegalArgumentException("Invalid");
		}

	}

}
