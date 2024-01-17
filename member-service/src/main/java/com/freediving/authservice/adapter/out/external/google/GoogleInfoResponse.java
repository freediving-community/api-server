package com.freediving.authservice.adapter.out.external.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : Google 소셜 로그인 후 응답 값 매핑
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31       sasca37       최초 생성
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleInfoResponse(
	Long sub,
	String name,
	String givenName,
	String familyName,
	String picture,
	String email,
	String emailVerified,
	String locale
) {
}
