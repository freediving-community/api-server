package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto;

import com.freediving.common.domain.member.RoleLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "유저 정보 프리다이빙 라이센스 정보")
public class FreeDiving {
	@Schema(description = "유저 권한", example = "0")
	public Integer roleLevel = RoleLevel.UNREGISTER.getLevel();

	@Schema(description = "유저 권한 코드", example = "UNREGISTER")
	public String roleLevelCode = RoleLevel.UNREGISTER.name();

	@Schema(description = "유저 라이센스 레벨 (null, 0 ~ 5)  null : 미입력, 0 : 자격증 없음, 1 : 1레벨,"
		+ "2 : 2레벨, 3 : 3레벨, 4 : 4레벨, 5 : 강사 ", example = "1")
	public Integer licenseLevel;

	@Schema(description = "유저 라이센스 관리자 승인 여부 (true, false)", example = "false")
	public Boolean licenseValidTF;
}
