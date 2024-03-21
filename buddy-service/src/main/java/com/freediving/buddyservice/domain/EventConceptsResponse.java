package com.freediving.buddyservice.domain;

import java.util.ArrayList;
import java.util.List;

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
public class EventConceptsResponse {

	private List<EventConcept> concepts = new ArrayList<>();

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public class EventConcept {
		private String key;
		private String name;
	}
}
