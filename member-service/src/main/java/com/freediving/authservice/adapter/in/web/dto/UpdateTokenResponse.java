package com.freediving.authservice.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author         : sasca37
 * @Date           : 2024/04/20
 * @Description    : 토큰 업데이트 요청에 대한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/04/20       sasca37       최초 생성
 */

@Schema(description = "토큰 업데이트 요청에 대한 응답 정보")
public record UpdateTokenResponse(
	@Schema(description = "액세스 토큰", example = "XXXX.XXXXX.XXX")
	String accessToken
) {
}
