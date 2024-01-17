package com.freediving.authservice.adapter.out.external.naver;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : Naver 소셜 로그인 후 응답 값 매핑
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31       sasca37       최초 생성
 */

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record NaverInfoResponse(
	String resultcode,
	String message,
	Response response
) {

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record Response(
		String id,
		String nickname,
		String name,
		String email,
		String gender,
		String age,
		String birthday,
		String profileImage,
		String birthyear,
		String mobile
	) {
	}
}
