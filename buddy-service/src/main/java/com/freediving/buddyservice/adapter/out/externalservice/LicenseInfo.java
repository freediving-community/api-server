package com.freediving.buddyservice.adapter.out.externalservice;

import com.freediving.common.domain.member.FreeDiving;

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
}
