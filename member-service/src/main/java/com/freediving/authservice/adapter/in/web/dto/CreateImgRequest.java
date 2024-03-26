package com.freediving.authservice.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : 이미지 PreSigned URL 정보를 요청하는 Request DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@Schema(description = "이미지 PreSigned URL 요청 정보")
public record CreateImgRequest(
	@Schema(description = "이미지 저장 디렉토리명", example = "license")
	@NotNull(message = "디렉토리명은 필수 입니다.")
	String directory,

	@Schema(description = "파일 확장자명", example = "jpg")
	@NotNull(message = "파일 확장자명은 필수 입니다.")
	String ext
) {
}
