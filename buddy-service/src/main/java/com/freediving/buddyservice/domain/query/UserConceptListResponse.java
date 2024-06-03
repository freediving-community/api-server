package com.freediving.buddyservice.domain.query;

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
@Schema(title = "사용자 선호 콘셉트 조회 결과", description = "GET /v1/internal/concept 사용자 선호 콘셉트 조회 결과", hidden = true)
public class UserConceptListResponse {
	private List<BuddyEventConcept> concepts;

	public void add(BuddyEventConcept concept) {
		if (concepts == null)
			this.concepts = new ArrayList<>();

		this.concepts.add(concept);
	}

}
