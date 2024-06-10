package com.freediving.communityservice.adapter.in.web;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@RequestScope
public class UserProvider {

	private Long requestUserId;
	// private RoleLevel roleLevel;

	public UserProvider() {
		this.requestUserId = -1L;
		// this.roleLevel = RoleLevel.ANONYMOUS;
	}

	public UserProvider(Long requestUserId /*, RoleLevel roleLevel*/) {
		this.requestUserId = requestUserId;
		// this.roleLevel = roleLevel != null ? roleLevel : RoleLevel.ANONYMOUS;
	}

	public boolean isValidUserId() {
		return this.requestUserId != null && this.requestUserId != -1L;
	}

	@Getter
	public enum RoleLevel {
		ANONYMOUS(-1),            // 비로그인 사용자

		UNREGISTER(5),            // 라이센스 미등록 사용자

		LEVEL0(10),
		LEVEL1(15),
		LEVEL2(20),
		LEVEL3(25),
		LEVEL4(30),
		INSTRUCTOR(35),

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

	// public void checkAdmin() {
	// 	if (!this.roleLevel.equals(RoleLevel.ADMIN))
	// 		throw new IllegalArgumentException("허용되지 않은 요청입니다.");
	// }
}
