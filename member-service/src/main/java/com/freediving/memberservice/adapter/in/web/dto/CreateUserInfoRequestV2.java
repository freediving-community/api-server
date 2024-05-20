package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;

import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.valid.EnumValid;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/19
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/19        sasca37       최초 생성
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "유저 회원 정보 등록 DTO V2")
public class CreateUserInfoRequestV2 {

	@Schema(description = "유저 라이센스 종류 (프리다이빙 F, 스쿠버다이빙 S)", example = "F")
	@EnumValid(enumClass = DiveType.class)
	private DiveType diveType;

	@Schema(description = "유저 라이센스 레벨 (0 ~ 5)", example = "1")
	private Integer licenseLevel;

	@Schema(description = "유저 라이센스 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String licenseImgUrl;

	@ArraySchema(schema = @Schema(type = "array", example = "DEEPSTATION"))
	private List<String> poolList;

	@ArraySchema(schema = @Schema(type = "array", example = "FUN"))
	private List<String> conceptList;

	@Schema(description = "유저 프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "유저 닉네임", example = "SASCA37")
	private String nickname;

	@Schema(description = "유저 자기소개 글", example = "안녕하세요.")
	private String content;

}
