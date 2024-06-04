package com.freediving.buddyservice.domain.query.common;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "컨셉 정보 응답")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ConceptInfoResponse {

	@Schema(description = "컨셉 ID", example = "PHOTO")
	private BuddyEventConcept conceptId;

	@Schema(description = "컨셉 이름", example = "사진촬영")
	private String conceptName;
}