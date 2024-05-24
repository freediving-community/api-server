package com.freediving.buddyservice.adapter.in.kafka.dto;

import java.util.Set;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "topic/buddyservice.task.user-concept-preferences 메시지")
public class ConceptPreference {
	@Schema(description = "사용자의 고유 ID", example = "12345")
	private Long userId;

	@Schema(description = "선호하는 버디 이벤트 컨셉들의 집합 \n 최대 2개입니다.   \n - TODO 예외 전파 ", example = "[\"FUN\", \"PHOTO\"]", implementation = BuddyEventConcept.class)
	private Set<String> preferredConcepts;
}