package org.freediving.gatewayservice.domain;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/23
 * @Description    : JWT 파싱 후 정보를 저장하기 위한 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/23        sasca37       최초 생성
 */
public record Token(String userId, String oauthType) {

	public static Token createToken(String userId, String oauthType) {
		return new Token(userId, oauthType);
	}
}
