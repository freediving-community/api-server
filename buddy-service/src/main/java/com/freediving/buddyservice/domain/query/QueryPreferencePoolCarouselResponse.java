package com.freediving.buddyservice.domain.query;

import java.util.ArrayList;
import java.util.List;

import com.freediving.buddyservice.domain.query.component.BuddyEventCarouselCardResponse;
import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 선호하는 풀장에 대한 버디 매칭을 추천하는 영역의 응답 값.
 *
 * @author 조준희
 * @version 1.0.0
 * 작성일 2024-04-06
 **/
@Schema(description = "선호 풀장 버디 매칭 추천 응답.")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryPreferencePoolCarouselResponse {

	@Schema(description = "선호 풀장 버디 매칭 추천 영역 타이틀 제목", example = "K26에 같이 갈래요?")
	private String title;

	@Schema(description = "선호 풀장 ID")
	private DivingPool divingPoolId;

	@Schema(description = "쿼리 컴포넌트 리스트", example = "[{...}, {...}]")
	private List<BuddyEventCarouselCardResponse> components = new ArrayList<>();

}
