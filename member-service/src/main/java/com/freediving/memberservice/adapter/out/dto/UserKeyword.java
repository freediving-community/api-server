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
@Schema(description = "유저 리뷰 키워드 응답 DTO")
@Builder
public class UserKeyword {

	@Schema(description = "키워드 이모지", example =  "😀")
	private String emoticon;
	@Schema(description = "키워드 제목", example =  "친절해요")
  	private String title;
	@Schema(description = "키워드 갯수", example =  "5")
	private Integer cnt;
}
