package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto;

import com.freediving.common.domain.member.RoleLevel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScubaDiving {
	protected Integer roleLevel = RoleLevel.UNREGISTER.getLevel();
	protected String roleLevelCode = RoleLevel.UNREGISTER.name();
	protected Integer licenseLevel;
	protected Boolean licenseValidTF;
}
