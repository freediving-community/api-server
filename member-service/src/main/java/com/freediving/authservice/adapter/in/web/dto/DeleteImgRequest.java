package com.freediving.authservice.adapter.in.web.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/03
 * @Description    : 이미지 삭제 Request DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */

@Schema(description = "이미지 삭제 요청 정보")
public record DeleteImgRequest(

	@ArraySchema(schema = @Schema(type = "array", example = "https://aws-s3.com/images/test1/1-km12k3mk.jpg"))
	@NotNull(message = "이미지 URL 리스트 정보는 필수 값입니다.")
	List<String> imgUrlList
) {
}
