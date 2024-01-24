package com.freediving.authservice.domain;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/23
 * @Description    : JWT 토큰 정보를 저장하기 위한 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/23        sasca37       최초 생성
 */
public record Token(String accessToken, String refreshToken) {

	public static Token createToken(String accessToken, String refreshToken) {
		return new Token(accessToken, refreshToken);
	}
}
