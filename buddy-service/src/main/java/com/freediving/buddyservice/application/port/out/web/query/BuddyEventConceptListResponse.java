package com.freediving.buddyservice.application.port.out.web.query;

import java.util.ArrayList;
import java.util.List;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 외부 서버스와 연동간에 버디 이벤트에 존재하는 컨셉을 응답하기 위한 DTO
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-03-21
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(title = "이벤트 콘셉트 조회 결과", description = "GET /v1/attribute/concept 이벤트 콘셉트 조회 결과", hidden = true)
public class BuddyEventConceptListResponse {
	@Schema(description = "콘셉트 리스트")
	private List<EventConcept> concepts;

	public void add(EventConcept concept) {
		if (concepts == null)
			this.concepts = new ArrayList<>();

		this.concepts.add(concept);
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class EventConcept {
		@Schema(description = "콘셉트 ID", type = "string", example = "FUN")
		private BuddyEventConcept conceptId;
		@Schema(description = "콘셉트 이름", example = "펀다이빙")
		private String conceptName;
		@Schema(description = "콘셉트 설명", example = "가볍게 즐기면서 하고 싶어요")
		private String conceptDesc;
	}
}
