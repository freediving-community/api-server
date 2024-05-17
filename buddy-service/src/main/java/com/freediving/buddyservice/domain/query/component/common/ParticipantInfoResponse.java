package com.freediving.buddyservice.domain.query.component.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ParticipantInfoResponse {
	private Long userId;
	private String profileImgUrl;
}
