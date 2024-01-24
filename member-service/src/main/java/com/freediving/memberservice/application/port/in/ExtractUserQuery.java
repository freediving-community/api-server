package com.freediving.memberservice.application.port.in;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : JWT 토큰 정보를 추출하기 위한 Query
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */
public record ExtractUserQuery(String email, String oauthType) {

	public static ExtractUserQuery createQuery(String email, String oauthType) {
		return new ExtractUserQuery(email, oauthType);
	}
}
