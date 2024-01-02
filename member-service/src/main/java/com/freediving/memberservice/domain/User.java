package com.freediving.memberservice.domain;

public record User(Long userId, OauthType oauthType, String email, String profileImgUrl) {

	public static User createUser(Long userId, OauthType oauthType, String email, String profileImgUrl) {
		return new User(userId, oauthType, email, profileImgUrl);
	}
}
