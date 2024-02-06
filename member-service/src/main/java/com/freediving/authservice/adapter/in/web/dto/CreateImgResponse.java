package com.freediving.authservice.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/06
 * @Description    : 이미지 PreSigned URL 정보를 응답하는 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/06       sasca37       최초 생성
 */

@Schema(description = "이미지 PreSigned URL 응답 정보")
public record CreateImgResponse(
	@Schema(description = "이미지 저장 요청할 PreSignedUrl 정보", example = "https://aws-s3.com")
	String preSignedUrl
) {
}
