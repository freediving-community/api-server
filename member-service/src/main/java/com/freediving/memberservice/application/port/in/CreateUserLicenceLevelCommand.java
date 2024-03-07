package com.freediving.memberservice.application.port.in;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/28
 * @Description    : Command 객체로 전환 및 SelfValidation
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/28        sasca37       최초 생성
 */
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateUserLicenceLevelCommand extends SelfValidating<CreateUserLicenceLevelCommand> {

	@NotNull
	private Integer licenceLevel;

	public CreateUserLicenceLevelCommand(Integer licenceLevel) {
		this.licenceLevel = licenceLevel;
		this.validateSelf();
	}
}
