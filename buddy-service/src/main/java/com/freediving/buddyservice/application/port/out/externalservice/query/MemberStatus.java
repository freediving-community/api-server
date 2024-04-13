package com.freediving.buddyservice.application.port.out.externalservice.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberStatus {
	private Long userid;
	private boolean isValid;
}
