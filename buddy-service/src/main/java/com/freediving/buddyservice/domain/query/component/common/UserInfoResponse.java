package com.freediving.buddyservice.domain.query.component.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
	private Long userId;
	private String profileUrl;
	private String nickname;
	private Integer freedivingLevel;
}