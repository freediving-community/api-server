package com.freediving.authservice.adapter.out.external.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : Kakao 사용자 정보를 얻기 위한 Token 응답 값 매핑
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31       sasca37       최초 생성
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KaKaoTokenResponse(
	String tokenType,
	String accessToken,
	String idToken,
	Integer expiresIn,
	String refreshToken,
	Integer refreshTokenExpiresIn,
	String scope
) {
}
