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
@Schema(description = "유저 스토리 응답 DTO")
@Builder
public class UserStoryResponse {

	@Schema(description = "유저 스토리 갯수", example = "5")
	private Integer storyCnt;

  	private List<UserStory> storyList;
}
