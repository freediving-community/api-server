package com.freediving.memberservice.adapter.out.dto;

import java.util.List;

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
@Schema(description = "유저 받은 후기 응답 DTO")
@Builder
public class UserReviewResponse {

	@Schema(description = "받은 후기 갯수", example = "6")
	private Integer reviewCnt;

	private List<UserKeyword> keywordList;
  	private List<UserReview> reviewList;
}
