package com.freediving.memberservice.domain;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : User 정보를 저장하는 불변 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

public record User(Long userId, OauthType oauthType, String email, String profileImgUrl,
				   String refreshToken, Boolean isNewUser) {

	public static User createSimpleUser(Long userId, OauthType oauthType, String email, String profileImgUrl,
		String refreshToken, Boolean isNewUser) {
		return new User(userId, oauthType, email, profileImgUrl, refreshToken, isNewUser);
	}
}
