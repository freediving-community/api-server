package com.freediving.memberservice.adapter.out.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Schema(description = "유저 리뷰 조회 응답 DTO")
@Builder
public class UserReview {

	@Schema(description = "유저 식별 키", example = "1")
	private Long userId;

	@Schema(description = "프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "초보다이버_00001")
	private String nickname;

	@Schema(description = "유저 라이센스 레벨 (null, 0 ~ 5)  null : 미입력, 0 : 자격증 없음, 1 : 1레벨,"
		+ "2 : 2레벨, 3 : 3레벨, 4 : 4레벨, 5 : 강사 ", example = "1")
	private Integer licenseLevel;

	@Schema(description = "버디 경험 횟수", example = "5")
	private Integer buddyExprCnt;

	@Schema(description = "리뷰 내용", example = "걱정이 많았는데, 진짜 즐거웠어요.")
	private String content;

	@Schema(description = "리뷰 작성 일자", example = "2024.06.29")
	private String writeYYYYMMDD;

	@Schema(description = "다이브 장소", example = "PARADIVE")
	private String pool;
}
