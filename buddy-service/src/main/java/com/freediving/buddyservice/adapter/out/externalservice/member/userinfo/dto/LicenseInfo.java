package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LicenseInfo {

	private FreeDiving freeDiving;
	private ScubaDiving scubaDiving;
}
